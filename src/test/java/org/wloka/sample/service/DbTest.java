package org.wloka.sample.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wloka.sample.data.Item;
import org.wloka.sample.data.Properties;
import org.wloka.sample.service.IPropertiesStore.DataAccessException;


public class DbTest {

    @Mock
    private IPropertiesStore mockedStore;

    @InjectMocks
    private DB testObj;

    @Before
    public void setUp() throws Exception {
        testObj = new DbTestClass();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void storeWithValidPathSavesItemInDatabase() throws Exception {
        String target = "/path";
        Item   item   = new Item(target);

        testObj.store(item);

        verify(mockedStore).save(eq(target), eq(item.getProperties()));
    }

    @Test
    public void storeWithNullItemDoesNotSaveItemInDatabase() throws Exception {
        testObj.store(null);

        verify(mockedStore, never()).save(anyString(), any(Properties.class));
    }

    @Test
    public void storeWithValidPathAndExceptionReturnsInstance() throws Exception {
        when(mockedStore.save(anyString(), any(Properties.class))).thenThrow(DataAccessException.class);

        DB actual = testObj.store(new Item("/path"));

        assertSame(testObj, actual);
    }

    @Test
    public void storeWithValidPathAndValidItemReturnsSameDatabase() throws Exception {
        Item item = new Item("/path");

        DB actual = testObj.store(item);

        assertSame(testObj, actual);
    }

    @Test
    public void storeWithNullItemReturnsSameDatabase() throws Exception {
        DB actual = testObj.store(null);

        assertSame(testObj, actual);
    }

    @Test
    public void getWithValidPathLoadsItemFromDatabase_DONTDOTHIS() throws Exception {
        String     target   = "/path/to/item";
        Properties expected = new Properties();
        when(mockedStore.load(eq(target))).thenReturn(expected);

        Item actual = testObj.get(target);

        // DON'T assert stubbed values, it's hidden behavior verification
        assertEquals(expected, actual.getProperties());
    }

    @Test
    public void getWithValidPathLoadsItemFromDatabase() throws Exception {
        String target = "/path/to/item";

        testObj.get(target);

        verify(mockedStore).load(eq(target));
    }

    @Test
    public void getWithNullPathDoesNotLoadItemFromDatabase() throws Exception {
        String target = null;

        testObj.get(target);

        verify(mockedStore, never()).load(anyString());
    }

    @Test
    public void getWithNullPathReturnsNull() throws Exception {
        String target = null;

        Item actual = testObj.get(target);

        assertNull(actual);
    }

    @Test
    public void deleteWithValidPathLoadsItemFromDatabase() throws Exception {
        String target = "/path/to/item";

        testObj.delete(target);

        verify(mockedStore).remove(eq(target));
    }

    @Test
    public void deleteWithNullPathDoesNotLoadItemFromDatabase() throws Exception {
        String target = null;

        testObj.delete(target);

        verify(mockedStore, never()).remove(anyString());
    }

    @Test
    public void deleteWithValidPathReturnsSameDatabase() throws Exception {
        String target = "/path/to/item";

        DB actual = testObj.delete(target);

        assertSame(testObj, actual);
    }

    @Test
    public void deleteWithNullPathReturnsSameDatabase() throws Exception {
        String target = null;

        DB actual = testObj.delete(target);

        assertSame(testObj, actual);
    }

    @Test
    public void deleteWithValidPathAndExceptionReturnsInstance() throws Exception {
        when(mockedStore.remove(anyString())).thenThrow(DataAccessException.class);

        DB actual = testObj.delete("/path/to/item");

        assertSame(testObj, actual);
    }

    @Test
    public void existsWithExistingPathInDbReturnsTrue() throws Exception {
        when(mockedStore.load(anyString())).thenReturn(new Properties());

        boolean actual = testObj.exists(new Item("/path/to/item"));

        assertTrue(actual);
    }

    @Test
    public void existsWithNonExistingPathInDbReturnsFalse() throws Exception {
        boolean actual = testObj.exists(new Item("/does/not/exists"));

        assertFalse(actual);
    }

    @Test
    public void existsWithExceptionFromDbReturnsFalse() throws Exception {
        when(mockedStore.load(anyString())).thenThrow(DataAccessException.class);

        boolean actual = testObj.exists(new Item("/path/to/item"));

        assertFalse(actual);
    }

    private static class DbTestClass extends DB {
        public DbTestClass() {
            super();
        }
    }
}