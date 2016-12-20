package com.smartsoft.ssutil.shared;

import org.junit.Test;
import com.smartsoft.ssutil.Path;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathTest {

    @Test
    public void test_isScheme() throws Exception {
        assertTrue(Path.isScheme("http://"));
        assertTrue(Path.isScheme("file://"));
        assertFalse(Path.isScheme("foo"));
        assertFalse(Path.isScheme("foo/boo"));
    }

    @Test
    public void test_hasScheme() throws Exception {
        assertTrue(Path.hasScheme("http//foo.boo"));
        assertTrue(Path.hasScheme("http//foo.boo/aaa/bbb"));
        assertFalse(Path.hasScheme("foo"));
        assertFalse(Path.hasScheme("foo/boo"));
    }

    @Test
    public void test_hasHttpScheme() throws Exception {
        assertTrue(Path.hasHttpScheme("http//foo.boo"));
        assertTrue(Path.hasHttpScheme("http//foo.boo/aaa/bbb"));
        assertFalse(Path.hasHttpScheme("foo"));
        assertFalse(Path.hasHttpScheme("foo/boo"));
    }

    @Test
    public void test1() throws Exception {

        Path CONTEXT_PATH_REPO = new Path("/configurator-content-v2");

        Path DOMAIN_IMG_SCION = Path.domain("media.scion.com");

        Path scheme = Path.httpScheme();

        Path path = Path.httpUrl(DOMAIN_IMG_SCION);
        Path BASE_URL_IMG_SCION = path.append(CONTEXT_PATH_REPO);

        System.err.println("BASE_URL_IMG_SCION[" + BASE_URL_IMG_SCION + "]");
    }

}
