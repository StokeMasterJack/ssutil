package com.smartsoft.ssutil.servlet.http.headers;

import com.google.common.base.Preconditions;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.logging.Logger;

public class CacheUtil {


    /**
     * Adds the following headers to an outgoing http response:
     * <p/>
     * Cache-Control: public, max-age=31536000   (60 years in future)
     * Expires: Wed, 15 Feb 2012 16:12:02 GMT   (1 year into future)
     * Last-Modified: Mon, 19 Apr 2010 22:39:21 GMT  (passed in as arg)
     */
    public static void addCacheForeverResponseHeaders(HttpServletResponse response) {
        addCacheForeverResponseHeaders(response, null);
    }

    /**
     * Adds the following headers to an outgoing http response:
     * <p/>
     * Cache-Control: public, max-age=31536000         -- 1 years in the future
     * Expires: Wed, 15 Feb 2012 16:12:02 GMT          -- 1 year into future
     * Last-Modified: Mon, 19 Apr 2010 22:39:21 GMT    -- shaFile.lastModified of the actual passed in shaFile (see note)
     * Edge-control: max-age=31536000                  -- Akamai-specific cache header
     * <p/>
     * Content-type: application/json                  -- getMimeTypeFromFile(shaFile) of the actual passed in shaFile
     * Content-length: 802                             -- shaFile.length() of the actual passed in shaFile
     * <p/>
     * Note: The shaFile.lastModified is also the creation date of the file
     * since sha files are (by definition) immutable
     */
    public static void addCacheForeverResponseHeaders(HttpServletResponse response, File shaFile) {

        //standard http cache-related headers

        CacheControl cc = new CacheControl();
        cc.setMaxAge(CacheControl.ONE_YEAR);  //31536000
        cc.setPublic(true);
        cc.addToResponse(response);

        Expires expires = Expires.expiresOneYearFromNow();
        expires.addToResponse(response);

        if (shaFile != null && shaFile.canRead()) {
            LastModified lastModified = new LastModified(shaFile.lastModified());
            lastModified.addToResponse(response);
        }

        //akamai-specific cache-related headers

        EdgeControl edgeControl = new EdgeControl();
        edgeControl.setMaxAge(EdgeControl.ONE_YEAR);
        edgeControl.setNoStore(false);
        edgeControl.setBypassCache(false);
        edgeControl.addToResponse(response);

        //other http headers

        if (shaFile != null && shaFile.canRead()) {
            response.setContentType(getMimeTypeFromFile(shaFile));
            response.setContentLength((int) shaFile.length());
        }

    }

    private static String getMimeTypeFromFile(File file) {
        if (file.getName().endsWith(".jpg")) {
            return "image/jpeg";
        } else if (file.getName().endsWith(".json")) {
            return "application/json";
        } else {
            throw new IllegalArgumentException("Unsupported file extension[" + file + "]");
        }
    }

    public static void addCacheForXHoursResponseHeaders(HttpServletResponse response, int hoursCdn, int hoursBrowser) {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(CacheControl.ONE_HOUR * hoursBrowser);
        cc.setSMaxAge(CacheControl.ONE_HOUR * hoursCdn);
        cc.setPublic(true);
        cc.addToResponse(response);

        int hoursExpires = Math.max(hoursBrowser, hoursCdn);
        Expires expires = Expires.expiresXHoursFromNow(hoursExpires);
        expires.addToResponse(response);

        //Akamai Specific
        EdgeControl edgeControl = new EdgeControl();
        edgeControl.setMaxAge(EdgeControl.ONE_HOUR * hoursCdn);
        edgeControl.setNoStore(false);
        edgeControl.setBypassCache(false);
        edgeControl.addToResponse(response);
    }

    public static void addCacheForXHoursResponseHeaders(HttpServletResponse response, int hours) {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(CacheControl.ONE_HOUR * hours);
        cc.setPublic(true);
        cc.addToResponse(response);

        Expires expires = Expires.expiresXHoursFromNow(hours);
        expires.addToResponse(response);

        //Akamai Specific
        EdgeControl edgeControl = new EdgeControl();
        edgeControl.setMaxAge(EdgeControl.ONE_HOUR * hours);
        edgeControl.setNoStore(false);
        edgeControl.setBypassCache(false);
        edgeControl.addToResponse(response);

    }

    public static void addCacheForXDaysResponseHeaders(HttpServletResponse response, int days) {

        CacheControl cc = new CacheControl();
        cc.setMaxAge(CacheControl.ONE_DAY * days);
        cc.setPublic(true);
        cc.addToResponse(response);

        Expires expires = Expires.expiresXDaysFromNow(days);
                expires.addToResponse(response);

        //Akamai Specific
        EdgeControl edgeControl = new EdgeControl();
        edgeControl.setMaxAge(EdgeControl.ONE_DAY * days);
        edgeControl.setNoStore(false);
        edgeControl.setBypassCache(false);
        edgeControl.addToResponse(response);

    }


    public static void addCacheNeverResponseHeaders(HttpServletResponse response) {
        Expires expires = Expires.expiresNow();
        expires.addToResponse(response);

        CacheControl cc1 = new CacheControl();
        cc1.setNoCache(true);
        cc1.setNoStore(true);
        cc1.setMaxAge(0);
        cc1.addToResponse(response);


        Pragma pragma = new Pragma();
        pragma.setNoCache(true);
        pragma.addToResponse(response);


        //Akamai Specific
        EdgeControl edgeControl = new EdgeControl();
        edgeControl.setNoStore(true);
        edgeControl.addToResponse(response);

    }

    public static boolean isDotCacheFile(ServletRequest req) {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestUri = request.getRequestURI();
        return requestUri != null && requestUri.contains(".cache.");
    }

    public static boolean isDotNocacheFile(ServletRequest req) {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestUri = request.getRequestURI();
        return requestUri != null && requestUri.contains(".nocache.");
    }


    public static boolean isCacheForeverRequestUri(String requestUri) {
        Preconditions.checkNotNull(requestUri);

        if (requestUri.contains(".cache.")) {
            return true;
        }

        if (requestUri.contains("/versionedContent/")) {
            return true;
        }

        if (isFingerprintUrl(requestUri)) {
            return true;
        }

        return false;


    }

    @Deprecated
    public static boolean isFingerprintUrl(String requestUri) {
        log.warning("isFingerprintUrl is Deprecated");
        return false;
    }

    private static Logger log = Logger.getLogger(CacheUtil.class.getName());

}
