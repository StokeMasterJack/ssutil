package com.smartsoft.ssutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


/**
 * A Cariage Return Line Feed
 */
public class CrLf {

    public static final byte[] asBytes = new byte[]{13, 10};
    public static final char[] asChars = new char[]{'\r', '\n'};
    public static final String asString = "\r\n";

    public static final int CR = 13;
    public static final int LF = 10;

    public static final String CR_LF_STRING = "\r\n";

    public static final String CR_STRING = "\r";
    public static final String LF_STRING = "\n";

    public static final char CR_CHAR = '\r';
    public static final char LF_CHAR = '\n';

    public static boolean endsWithCrLf(StringBuffer sb) {
        if (sb == null) return false;
        int L = sb.length();
        if (L < 2) return false;
        if (sb.charAt(L - 2) != CR) return false;
        if (sb.charAt(L - 1) != LF) return false;
        return true;
    }

    public static boolean endsWithCrLfCrLf(StringBuffer sb) {
        if (sb == null) return false;
        int L = sb.length();
        if (L < 4) return false;
        if (sb.charAt(L - 4) != CR) return false;
        if (sb.charAt(L - 3) != LF) return false;
        if (sb.charAt(L - 2) != CR) return false;
        if (sb.charAt(L - 1) != LF) return false;
        return true;
    }

    /**
     * Used to indicate the end of a mail message
     */
    public static boolean endsWithCrLfPeriodCrLf(StringBuffer sb) {
        if (sb == null) return false;
        int L = sb.length();
        if (L < 5) return false;
        if (sb.charAt(L - 5) != CR) return false;
        if (sb.charAt(L - 4) != LF) return false;
        if (sb.charAt(L - 3) != '.') return false;
        if (sb.charAt(L - 2) != CR) return false;
        if (sb.charAt(L - 1) != LF) return false;
        return true;
    }

//    public static String readLine(InputStream is, int max) throws IOException {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < max; i++) {
//            int b = is.read();
//            if (b == -1) break;
//            char c = (char) b;
//            sb.append(c);
//            if (CrLf.endsWithCrLf(sb)) break;
//        }
//        return sb.toString().trim();
//    }

    public static String readLine(InputStream is, int max) throws IOException {
        return StringUtil.readUntil(is, CrLf.asString, max);
    }

    public static String readLine(Reader r, int max) throws IOException {
        return StringUtil.readUntil(r, CrLf.asString, max);
    }

    public static String readLine(InputStream is) throws IOException {
        return readLine(is, 1000);
    }

    public static String readLine(Reader r) throws IOException {
        return readLine(r, 1000);
    }

    public static void renderLf(Appendable a) {
        try {
            a.append(LF_STRING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void renderCrLf(Appendable a) {
        try {
            a.append(CR_LF_STRING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
