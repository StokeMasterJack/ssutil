package com.smartsoft.ssutil.shared;

import org.junit.Test;
import com.smartsoft.ssutil.Strings;

import static org.junit.Assert.assertEquals;

public class StringsTest {

    @Test
    public void test_containsHowMany() throws Exception {
        String outer = "//dave//joe//dave//";

        int slashSlashCount = Strings.containsHowMany(outer, "//");
        int daveCount = Strings.containsHowMany(outer, "dave");
        int joeCount = Strings.containsHowMany(outer, "joe");

        assertEquals(slashSlashCount,4);
        assertEquals(daveCount,2);
        assertEquals(joeCount,1);


        outer = "http://";
        slashSlashCount = Strings.containsHowMany(outer, "//");
        assertEquals(slashSlashCount,1);
    }

}
