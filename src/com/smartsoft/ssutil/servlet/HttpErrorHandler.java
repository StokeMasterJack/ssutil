package com.smartsoft.ssutil.servlet;

import com.google.common.base.Charsets;
import com.smartsoft.ssutil.CrLf;
import com.smartsoft.ssutil.Template;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

public class HttpErrorHandler {

    public static final Charset CHARSET_UTF8 = Charsets.UTF_8;

    public static final String RESPONSE_ALREADY_COMMITTED = "Response already committed. No message will be sent back to client.";
    public static final String SENDING_ERROR_RESPONSE_TO_CLIENT = "Sending http error response to client...";
    public static final String SENDING_ERROR_RESPONSE_TO_CLIENT_COMPLETE = "Error response has been send to client!";
    public static final String PROBLEM_SENDING_500_RESPONSE = "Problem " + SENDING_ERROR_RESPONSE_TO_CLIENT;

    public final static String LF = CrLf.LF_STRING;

    public static final String TEXT_PLAIN = "text/plain";
    public static final String UTF_8 = "UTF-8";


    public HttpErrorTemplate template;
    public ServletContext servletContext;
    public final Logger log;
    public final String servletName;
    public final int statusCode;

    public HttpErrorHandler(ServletContext servletContext, Logger log, String servletName, int statusCode) {
        checkNotNull(servletContext);
        checkNotNull(log);
        checkNotNull(servletName);

        this.servletContext = servletContext;
        this.log = log;
        this.servletName = servletName;
        this.statusCode = statusCode;

    }

    public void handleError(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        checkNotNull(request);
        checkNotNull(response);
        checkNotNull(exception);

        RequestContext ctx = new RequestContext(request, response, exception);

        handleError(ctx);
    }


    private static final String GENERIC_STRING_FAILURE_MSG = "GENERIC_STRING_FAILURE_MSG";
    private static final byte[] GENERIC_BYTES_FAILURE_MSG = "GENERIC_BYTES_FAILURE_MSG".getBytes(CHARSET_UTF8);

    public void handleError(RequestContext ctx) {
        initTemplate();

        HttpServletResponse response = ctx.getResponse();

        log("Unexpected exception handling request.", ctx.exception);

        try {
            try {
                response.reset();
            } catch (IllegalStateException resetException) {
                  /*
                   * If we can't reset the request, the only way to signal that something
                   * has gone wrong is to throw an exception from here. It should be the
                   * case that we call the user's implementation code before emitting data
                   * into the response, so the only time that gets tripped is if the object
                   * serialization code blows up.
                   */
                throw new RuntimeException("Response already committed. No message will be sent back to client.", ctx.exception);
            }


            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            final String clientResponse = buildClientResponse(ctx);
            log(clientResponse);

            try {
                response.getOutputStream().write(clientResponse.getBytes(CHARSET_UTF8));
            } catch (IllegalStateException osWriteFailed) {
                // Handle the (unexpected) case where getWriter() was previously used
                response.getWriter().write(clientResponse);
            }


//            sendError(ctx);
//            ctx.log("Error response has been send to client!");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Problem sending error response to client!");
        }


    }

    protected String buildClientResponse(RequestContext ctx) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);

        renderClientResponse(ctx, out);

        out.flush();
        return stringWriter.toString();
    }

    protected void renderClientResponse(RequestContext ctx, PrintWriter out) {
        renderClientResponseMainMessage(ctx, out);
        renderClientResponseStacktrace(ctx, out);
    }

    protected void renderClientResponseMainMessage(RequestContext ctx, PrintWriter out) {
        out.println("Internal Server Error ");
        out.println("\t Name of request handler: " + servletName);
        out.println("\t requestUri: " + ctx.getRequestUri());
        out.println("\t queryString: " + ctx.getQueryString());
        out.println();
    }

    protected void renderClientResponseStacktrace(RequestContext ctx, PrintWriter out) {
        ctx.exception.printStackTrace(out);
    }

    private void initTemplate() {
        if (template == null) {
            template = new HttpErrorTemplate();
        }
    }

    protected int getStatusCode() {
        return statusCode;
    }

    class HttpErrorTemplate {

        public static final String HANDLER = "handler";
        public static final String REQUEST_URI = "requestUri";
        public static final String QUERY_STRING = "queryString";

        Template out = new Template();

        HttpErrorTemplate() {
            out.println("Internal Server Error. ");
            out.print("\t Unexpected exception in [${handler}] ");
            out.print("\t while processing requestUri[${requestUri}] with queryString[${queryString}]. ");
            out.print("\t See stacktrace for more details");
        }

        public Map<String, String> buildTemplateParams(RequestContext ctx) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put(HANDLER, servletName);
            m.put(REQUEST_URI, ctx.getRequestUri());
            m.put(QUERY_STRING, ctx.getQueryString());
            return m;
        }

        public String format(Map<String, String> params) {
            return out.format(params);
        }

        public String format(RequestContext ctx) {
            Map<String, String> map = buildTemplateParams(ctx);
            return format(map);
        }


    }

    protected void sendError(RequestContext ctx) throws IOException {


        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);

        sendErrorMainMessage(ctx, out);
        out.println();
        sendErrorStackTrace(ctx, out);
    }


    protected void sendErrorMainMessage(RequestContext ctx, PrintWriter out) {
        String msg = renderMainMessage(ctx);
        out.println(msg);
    }

    protected String renderMainMessage(RequestContext ctx) {
        return template.format(ctx);
    }

    protected void sendErrorStackTrace(RequestContext ctx, PrintWriter out) {
        ctx.exception.printStackTrace(out);
    }

    public void log(int msg) {
        log(msg + "");
    }

    public void log(String msg) {
        log(msg, null);
    }

    public void log(String msg, Throwable e) {
        try {
            if (e != null) {
                log.log(Level.SEVERE, msg, e);
            } else {
                log.log(Level.SEVERE, msg);
            }
        } catch (Exception e1) {
            System.err.println("HOLY CRAP: "); //todo
            e1.printStackTrace();
        }

        if (servletContext != null) {
            try {
                servletContext.log(msg, e);
            } catch (Exception e1) {
                System.err.println("HOLY CRAP: "); //todo
                e1.printStackTrace();
            }
        }
    }


    public static String serializeException(RuntimeException e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        e.printStackTrace(out);
        out.flush();
        return stringWriter.toString();
    }


}

