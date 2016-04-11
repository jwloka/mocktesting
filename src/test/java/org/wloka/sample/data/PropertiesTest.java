package org.wloka.sample.data;


import static org.junit.Assert.*;

import org.junit.Test;


public class PropertiesTest {
    @Test
    public void hasPropertyWithExistinKeyReturnsTrue() throws Exception {
        Properties testObj = new Properties().setValue("foo", new Object());

        assertTrue(testObj.hasProperty("foo"));
    }

    @Test
    public void setValueWithValidKeyButNullValueDoesNotYieldProperty() throws Exception {
        Properties testObj = new Properties().setValue("foo", null);

        assertFalse(testObj.hasProperty("foo"));
    }

    @Test
    public void getValueWithExistingKeyReturnsPropertyValue() throws Exception {
        Object expected = new Object();
        Properties testObj  = new Properties().setValue("foo", expected);

        Object actual = testObj.getValue("foo");

        assertSame(expected, actual);
    }

    @Test(expected = InvalidDataException.class)
    public void getValueWithNonExistingKeyThrowsException() throws Exception {
        new Properties().getValue("non-existing");
    }
}