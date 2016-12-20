package com.smartsoft.ssutil.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RHF {

    private static RequestHandlerFactory rhf;

    public static void setRhf(RequestHandlerFactory rhf) {
        RHF.rhf = rhf;
    }

    public static void handleRequest(HttpServletRequest request, HttpServletResponse response, Class<? extends RequestHandler> cls) {
        if (rhf == null) {
            throw new IllegalStateException("Must call " + RHF.class.getSimpleName() + ".setRhf(..) before calling " + RHF.class.getSimpleName() + ".handleRequest(..)");
        }
        RequestHandler rh = rhf.createRequestHandler(cls);
        rh.handle(request, response);
    }


}
