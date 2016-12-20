package com.smartsoft.ssutil;

public class Assert {

    static public void True(boolean b) throws AssertionException {
        if (!b) throw new AssertionException();
    }

    static public void True(boolean b, String msg) throws AssertionException {
        if (!b) throw new AssertionException(msg);
    }

    static public void False(boolean b) throws AssertionException {
        if (b) throw new AssertionException();
    }

    static public void False(boolean b, String msg) throws AssertionException {
        if (b) throw new AssertionException(msg);
    }

    public static void notNull(Object o, String msg, Object sceneOfCrime) throws AssertionException {
        if (o == null) throw new AssertionException(msg, sceneOfCrime);
    }

    public static void notNull(Object o, String msg) throws AssertionException {
        if (o == null) throw new AssertionException(msg);
    }

    public static void notNull(Object o) throws AssertionException {
        if (o == null) throw new AssertionException();
    }

    public static void notBlank(String s) throws AssertionException {
        if (StringUtil.isBlank(s)) throw new AssertionException();
    }

    public static void notBlank(String s, String msg) throws AssertionException {
        if (StringUtil.isBlank(s)) throw new AssertionException(msg);
    }

    public static void Null(Object o, String msg) {
        if (o != null) throw new AssertionException(msg);
    }

    public static void equals(Object o1, Object o2) {
        if (!o1.equals(o2)) throw new AssertionException("Expected: " + o1 + " got: " + o2);

    }

    public static void illegalState() throws IllegalStateException {
        throw new IllegalStateException();
    }

    public static void illegalState(String msg) throws IllegalStateException {
        throw new IllegalStateException(msg);
    }

    public static <T> T illegalState2(String msg) throws IllegalStateException {
        throw new IllegalStateException(msg);
    }

    public static <T> T illegalState2() throws IllegalStateException {
        throw new IllegalStateException();
    }
}
