package com.smartsoft.ssutil.servlet;

import com.google.common.base.Splitter;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.smartsoft.ssutil.servlet.http.headers.CacheUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 *
 * Usage scenario #1:
 *
 *      <%
 *      AppCache appCache = new AppCache(request);
 *      appCache.addCachedFile("p1.html");
 *      appCache.addCachedFile("indexedDbDemo.js");
 *      appCache.writeJspResponse(pageContext);
 *      %>
 *
 * Usage scenario #2 :
 *
 *      <% smartsoft.util.servlet.AppCache.auto(pageContext); %>
 *
 */
public class AppCache {

    private static Splitter splitter = Splitter.on(":").trimResults().omitEmptyStrings();

    private static HashFunction hashFunction = Hashing.goodFastHash(64);
    private static final String CONTENT_TYPE = "text/cache-manifest";
    private static final String APP_CACHE_JSP_LOCAL_NAME = "appCache.jsp";
    private static final String APP_CACHE_PROP_FILE_NAME = "appCache.properties";

    private final HttpServletRequest request;
    private final Hasher hasher = hashFunction.newHasher();
    private String hashString;

    private final File baseDir;
    private final LinkedHashMap<String, File> cachedFiles = new LinkedHashMap<String, File>();   //localName -> full path

    public AppCache(ServletRequest request) {
        this.request = (HttpServletRequest) request;
        this.baseDir = getBaseDir();
    }

    public String getHashString() {
        if (hashString == null) {
            HashCode hashCode = hasher.hash();
            hashString = hashCode.toString();
        }
        return hashString;
    }

    public File getRealPath() {
        String servletPath = request.getServletPath();
        ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath(servletPath);
        return new File(realPath);
    }

    public File getRealWebAppDir() {
        ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath("/");
        return new File(realPath);
    }

    public File getPropsFile() {
        File realWebAppDir = getRealWebAppDir();
        return new File(realWebAppDir, AppCache.class.getSimpleName() + ".properties");
    }

    public boolean isDevMode() {
        File propsFile = getPropsFile();
        if (!propsFile.canRead()) {
            return false;
        }

        Properties pp = null;
        try {
            StringReader stringReader = new StringReader(Files.toString(propsFile, Charset.defaultCharset()));
            pp = new Properties();
            pp.load(stringReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String devMode = pp.getProperty("devMode");
        return devMode != null && devMode.trim().toLowerCase().equals("true");

    }

    public File getBaseDir() {
        File realPath = getRealPath();
        return realPath.getParentFile();
    }

    /**
     * Assumes all files in same dir as jsp (excluding hidden and appCache.jsp)
     */
    public void autoAddCacheFiles() {
        File[] files = baseDir.listFiles();
        for (File fullPath : files) {
            if (fullPath.isDirectory()) continue;
            if (fullPath.isHidden()) continue;
            if (fullPath.getName().equals(APP_CACHE_JSP_LOCAL_NAME)) continue;
            if (fullPath.getName().equals(APP_CACHE_PROP_FILE_NAME)) continue;
            addCachedFile(fullPath);
        }
    }

    public static void auto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AppCache appCache = new AppCache(request);
        appCache.autoAddCacheFiles();
        appCache.writeServletResponse(response);
    }

    public static void writeManifestResponseHeaders(HttpServletResponse response) {
        CacheUtil.addCacheNeverResponseHeaders(response);
        response.setContentType(CONTENT_TYPE);
    }

    public void addCachedFile(File fullPath) {
        String sFullPath = fullPath.getAbsolutePath();
        String sBaseDir = baseDir.getAbsolutePath();
        String localName = sFullPath.replace(sBaseDir, "");
        if (localName.startsWith("/")) {
            localName = localName.substring(1);
        }
        putCachedFile(localName, fullPath);
    }

    public void addCachedFile(String localFileName) {
        if (hashString != null) {
            throw new IllegalStateException();
        }

        File f = new File(baseDir, localFileName);
        if (!f.canWrite()) {
            throw new IllegalArgumentException("Cannot read cache file: " + f);
        }

        putCachedFile(localFileName, f);


    }

    private void putCachedFile(String localFileName, File f) {
        cachedFiles.put(localFileName, f);

        byte[] b;
        try {
            b = Files.toByteArray(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hasher.putBytes(b);
    }

    public void writeManifestBody(PrintWriter out) {
        out.println("CACHE MANIFEST");
        out.println("#" + getHashString());

        if (isDevMode()) {
            //nothing
        } else {
            out.println();
            out.println("CACHE:");
            for (String cacheFile : cachedFiles.keySet()) {
                out.println(cacheFile);
            }

            out.println();
            out.println("FALLBACK:");

            out.println();
            out.println("NETWORK:");
            out.println("*");
        }
    }

//    public void writeManifestBody(JspWriter out) {
//        PrintWriter printWriter = new PrintWriter(out);
//        writeManifestBody(printWriter);
//        printWriter.flush();
//    }
//
//    public void writeJspResponse(PageContext pageContext) {
//        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
//        JspWriter out = pageContext.getOut();
//        writeManifestResponseHeaders(response);
//        writeManifestBody(out);
//    }

    public void writeServletResponse(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        writeManifestResponseHeaders(response);
        writeManifestBody(out);
    }

}
