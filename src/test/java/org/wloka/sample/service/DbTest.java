package org.wloka.sample.service;


import static org.junit.Assert.assertEquals;
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


public class DbTest {

    @Mock
    private IPropertiesStore mockedStore;

    @InjectMocks
    private DB testObj;

    @Before
    public void setUp() throws Exception {
        testObj = DB.init();  // FIXME: Actual DB setup is loaded atleast once
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
}