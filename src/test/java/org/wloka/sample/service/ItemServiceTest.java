package org.wloka.sample.service;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.wloka.sample.data.Item;


public class ItemServiceTest {

    private DB mockedData;

    private ItemService testObj;

    @Before
    public void setUp() throws Exception {
        mockedData = mock(DB.class);
        testObj = new ItemService(mockedData);
    }

    @Test
    public void createItemWithNonExistingPathStoresItemInDatabase() throws Exception {
        Item target = new Item("path/to/item");

        testObj.createItem(target);

        verify(mockedData).store(eq(target));
    }

    @Test(expected = ItemResolverException.class)
    public void createItemWithExistingPathThrowsException() throws Exception {
        when(mockedData.exists(any(Item.class))).thenReturn(true);

        testObj.createItem(new Item("path/to/item"));
    }

    @Test
    public void getItemWithExistingPathLoadsItemFromDatabase() throws Exception {
        // Arrange
        Item target = new Item("path/to/item");
        when(mockedData.get(anyString())).thenReturn(target);

        // Act
        testObj.getItem(target.getPath());

        // Assert
        verify(mockedData).get(eq(target.getPath()));
    }

    @Test(expected = ItemResolverException.class)
    public void getItemWithNonExistingPathThrowsException() throws Exception {
        Item target = new Item("path/to/item");

        testObj.getItem(target.getPath());
    }

    @Test
    public void updateItemWithValidPathStoresItemInDatabase() throws Exception {
        Item target = new Item("path/to/item");

        testObj.updateItem(target);

        verify(mockedData).store(eq(target));
    }
}
