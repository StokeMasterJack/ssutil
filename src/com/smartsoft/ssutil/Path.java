package com.smartsoft.ssutil;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.smartsoft.ssutil.Strings.isEmpty;

/**
 * Represents a path that may be used on multiple platforms. i.e. file systems (Mac, Windows, Unix) and web URLs
 */
public class Path implements Comparable<Path>, Serializable {

    private final static Joiner JOINER = Joiner.on('/');
    public static final Path NULL_PATH = new Path();

    //will never have leading or trailing slashes
    private String pathString;
    public static final CharMatcher SCHEME_INVALID_CHARS = CharMatcher.noneOf("/\\.");

    public Path() {
        assert isValid();
    }

    public Path(String u) {
        pathString = fixUp(u);
        assert isValid();
    }

    public Path(Iterable segmentList) {
        this(JOINER.join(segmentList));
    }

    public Path(Path context, Path localPath) {
        this(context == null ? null : context.pathString, localPath == null ? null : localPath.pathString);
        assert isValid();
    }

    public Path(Path context, String pathString) {
        this(context == null ? null : context.pathString, pathString);
        assert isValid();
    }

    public Path(String context, String localPath) {
        String u1 = fixUp(context);
        String u2 = fixUp(localPath);

        if (u1 == null && u2 == null) {
            pathString = null;
        } else if (u1 != null && u2 == null) {
            pathString = u1;
        } else if (u1 == null && u2 != null) {
            pathString = u2;
        } else if (u1 != null && u2 != null) {
            if (isScheme(u1)) {
                checkState(u1.endsWith("//"));
                pathString = u1 + "" + u2;
            } else {
                pathString = u1 + "/" + u2;
            }
        } else {
            throw new IllegalStateException();
        }

        assert isValid();
    }


    private void fixUp() {
        this.pathString = fixUp(this.pathString);
    }

    private void check() {

    }

    private static void check(String u) {
        if (u == null) return;
        if (Strings.isEmpty(u)) throw new IllegalStateException("Failed isEmpty test for path: [" + u + "]");
        if (u.contains(" ")) throw new IllegalStateException("Failed containsSpace test for path: [" + u + "]");
        if (u.contains("\\")) throw new IllegalStateException("Failed containsBckSlash test for path: [" + u + "]");
        if (u.startsWith("//"))
            throw new IllegalStateException("Failed startsWithDoubleFwdSlash test for path: [" + u + "]");
    }

    public boolean isNull() {
        return pathString == null;
    }

    private boolean isValid() {
        try {
            check();
            return true;
        } catch (IllegalStateException e) {
//            return false;
            throw e;
        }
    }

    public static String fixUp(String u) {
        if (Strings.isEmpty(u)) {
            return null;
        } else {
            u = " " + u + " ";
            u = u.trim();
            u = convertBackslashesToForward(u);
            u = removeWindowsDriveLetterPrefix(u);

            if (!isScheme(u)) {
                u = trimSlashes(u);
            }

            check(u);
            return u;
        }
    }

    public static String removeWindowsDriveLetterPrefix(String u) {
        return u.replaceAll("c:", "");
    }

    public static String convertBackslashesToForward(String u) {
        return u.replaceAll("\\\\", "/");
    }

    public boolean isScheme() {
        return isScheme(pathString);
    }

    public static boolean isScheme(String pathString) {
        if (Strings.isEmpty(pathString)) {
            return false;
        }
        if (!pathString.endsWith("://")) {
            return false;
        }
        int slashSlashCount = Strings.containsHowMany(pathString, "://");
        if (slashSlashCount != 1) {
            return false;
        }

        String prefix = pathString.substring(0, pathString.length() - "://".length());

        return SCHEME_NAMES.contains(prefix);
    }

    public static boolean hasHttpScheme(String pathString) {
        if (!hasScheme(pathString)) {
            return false;
        }
        return pathString.startsWith("http");
    }

    public static boolean hasScheme(String pathString) {
        if (Strings.isEmpty(pathString)) {
            return false;
        }
        if (isScheme(pathString)) {
            return true;
        }

        if (!pathString.contains("//")) {
            return false;
        }

        int i1 = pathString.indexOf("//");

        if (i1 <= 2) {
            return false;
        }

        int i2 = pathString.lastIndexOf("//");

        if (i1 != i2) {
            return false;
        }

        String[] split = pathString.split("//");
        if (split.length != 2) {
            return false;
        }

        String prefix = split[0];

        return SCHEME_NAMES.contains(prefix);

    }

    public static boolean isValidSchemeName(String schemeName) {
        return SCHEME_NAMES.contains(schemeName);
    }

    public static final ImmutableSet<String> SCHEME_NAMES = ImmutableSet.of("http", "ftp", "file");

    public boolean isValidScheme(String schemeName) {
        return schemeName.contains(schemeName);
    }

    public static boolean isUrl(String s) {
        if (s.startsWith("http:/")) return true;
        else if (s.startsWith("file:/")) return true;
        else return false;
    }

    public boolean getPathString() {
        return isUrl(pathString);
    }


    @Override
    public String toString() {
        assert isValid();
        if (pathString == null) return "";
        if (pathString.startsWith("http:")) return pathString;
        if (pathString.startsWith("file:")) return pathString;
        return "/" + pathString;
    }

    public String toStringNoLeadingSlash() {
        assert isValid();
        if (pathString == null) return "";
        if (pathString.startsWith("http:")) return pathString;
        if (pathString.startsWith("file:")) return pathString;
        return pathString;
    }

    public static String trimSlashes(String s) {
        String a = trimLeadingSlash(s);
        String b = trimTrailingSlash(a);
        return b;
    }

    public static String trimTrailingSlash(String pathString) {
        if (pathString.endsWith("/")) return pathString.substring(0, pathString.length() - 1);
        return pathString + "";
    }

    public static String trimLeadingSlash(String s) {
        if (s.startsWith("/")) return s.substring(1);
        return s + "";
    }

    public Path copy() {
        return new Path(pathString);
    }

    public Path append(Path url) {
        if (url == null || url.isNull()) {
            return this;
        }
        return new Path(this, url);
    }

    public Path prepend(Path url) {
        return new Path(url, this);
    }

    public Path appendScheme(String scheme) {
        checkState(isNull());
        return Path.scheme(scheme);
    }

    public Path appendHttpScheme() {
        return append("http://");
    }

    public Path appendDomain(String domain) {
        checkState(isScheme());
        Path pDomain = Path.domain(domain);
        return append(pDomain);
    }

    public Path appendHttp(String domain) {
        if (domain == null) {
            return this;
        }
        checkState(isNull());
        checkNotNull(domain);
        Path httpUrl = Path.httpUrl(domain);
        return append(httpUrl);
    }

    public Path prependHttp(String domain) {
        Path httpUrl = Path.httpUrl(domain);
        return httpUrl.append(this);
    }

    public Path url(String scheme, String domain, String path) {
        return url(scheme, domain).append(path);
    }

    public Path url(String scheme, String domain, Path path) {
        return url(scheme, domain).append(path);
    }


    public boolean isUrl() {
        return isUrl(pathString);
    }


    public static Path scheme(String scheme) {
        checkNotNull(scheme);
        scheme = scheme.trim();
        checkArgument(isValidSchemeName(scheme));
        return new Path(scheme + "://");
    }

    public static Path httpScheme() {
        return scheme("http");
    }

    public static Path domain(String domain) {
        checkNotNull(domain);
        domain = domain.trim();
        checkArgument(domain.indexOf('/') == -1);
        checkArgument(domain.indexOf('\\') == -1);
        return new Path(domain.trim());
    }

    public static Path url(String scheme, String domain) {
        Path s = Path.scheme(scheme);
        Path d = Path.domain(domain);
        return url(s, d);
    }

    public static Path url(Path scheme, Path domain) {
        checkState(scheme.isScheme(), scheme);
        checkState(domain.isDomain(), domain);
        Path path = new Path(scheme, domain);
        checkState(path.isUrl());
        return path;
    }

    private boolean isDomain() {
        return true;
    }

    public static Path httpUrl(String domain) {
        return Path.url("http", domain);
    }

    public static Path httpUrl(Path domain) {
        Path scheme = httpScheme();
        return Path.url(scheme, domain);
    }


    public static Path httpUrl(String domain, Path path) {
        return Path.httpUrl(domain).append(path);
    }

    public static Path httpUrl(String domain, String path) {
        return Path.httpUrl(domain).append(path);
    }

    public Path append(String url) {
        if (url == null) {
            return this;
        }
        return new Path(this, new Path(url));
    }

    public Path appendExt(String ext) {
        return appendName(ext);
    }

    public Path append(int url) {
        return new Path(this, new Path(url + ""));
    }

    public Path prepend(String url) {
        return new Path(new Path(url), this);
    }

    public Path appendName(String suffix) {
        String s = this.pathString + suffix;
        return new Path(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path that = (Path) o;

        assert this.isValid();
        assert that.isValid();
        if (!pathString.equals(that.pathString)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pathString.hashCode();
    }

    public int compareTo(Path that) {
        if (that == null) throw new IllegalArgumentException("\"o\" is required");
        return this.pathString.compareTo(that.pathString);
    }

    public Path leftTrim(Path path) {
        String leftString = path.toString();
        String thisString = toString();
        return new Path(thisString.substring(leftString.length()));
    }

    public boolean endsWith(String s) {
        if (pathString == null) return false;
        return pathString.endsWith(s);
    }

    /**
     * up one folder
     * @return
     */
    public Path dotDot() {
        if (pathString == null) return this;

        int i = pathString.lastIndexOf('/');
        if (i == -1) return this;

        String s = pathString.substring(0, i);
        return new Path(s);
    }

    public boolean isHttpUrl() {
        if (pathString == null) {
            return false;
        } else {
            return pathString.toLowerCase().startsWith("http");
        }
    }


    public static Path nullPath() {
        return NULL_PATH;
    }
}
