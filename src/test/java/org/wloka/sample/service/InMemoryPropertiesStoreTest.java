package org.wloka.sample.service;


import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.wloka.sample.data.Properties;
import org.wloka.sample.service.IPropertiesStore.DataAccessException;


public class InMemoryPropertiesStoreTest {
    
    @Test(expected = DataAccessException.class)
    public void loadWithNonExistingPathThrowsException() throws Exception {
        new InMemoryPropertiesStore().load("doesnotexist");
    }

    @Test
    public void loadWithExistingPathReturnsValidProperties() throws Exception {
        Properties expected = new Properties();

        Properties actual = new InMemoryPropertiesStore().save("target", expected).load("target");

        assertSame(expected, actual);
    }

    @Test
    public void saveWithNonExistingPathYieldsValidProperties() throws Exception {
        Properties expected = new Properties();

        Properties actual = new InMemoryPropertiesStore().save("target", expected).load("target");

        assertSame(expected, actual);
    }

    @Test(expected = DataAccessException.class)
    public void saveWithExistingPathThrowsException() throws Exception {
        Properties expected = new Properties();

        new InMemoryPropertiesStore().save("target", expected).save("target", expected);
    }

    @Test
    public void removeWithExistingPathRemovesPropertiesFromStore() throws Exception {
        InMemoryPropertiesStore testObj = new InMemoryPropertiesStore().save("target", new Properties()).remove("target");

        try {
            testObj.load("target");
            fail();
        } catch (DataAccessException expected) {
            // passed
        }
    }

    @Test
    public void removeWithNonExistingPathDoesNotThrowException() throws Exception {
        InMemoryPropertiesStore testObj = new InMemoryPropertiesStore().remove("target");

        try {
            testObj.load("target");
            fail();
        } catch (DataAccessException expected) {
            // passed
        }
    }
}