package org.wloka.sample.data;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;


public class ItemTest {
    @Test
    public void ctorWithValidStringYieldsItemWithPath() throws Exception {
        Item testObj = new Item("expected");

        assertEquals("expected", testObj.getPath());
    }

    @Test(expected = InvalidDataException.class)
    public void ctorWithInvalidStringThrowsException() throws Exception {
        new Item("");
    }

    @Test
    public void setPropertiesWithValidPropertiesYieldsNewProperties() throws Exception {
        Properties expected = new Properties();

        Item testObj = new Item("/path").setProperties(expected);

        assertSame(expected, testObj.getProperties());
    }

    @Test(expected = InvalidDataException.class)
    public void setPropertiesWithInvalidPropertiesThrowsException() throws Exception {
        new Item("/path").setProperties(null);
    }
}