package org.wloka.sample.service;


import org.wloka.sample.data.Item;


public class ItemService {

    private DB storage;

    public ItemService() {
        // FIXME: Remove DB initialization from ctor
        this(DB.init());
    }

    public ItemService(DB database) {
        this.storage = database;
    }

    public Item createItem(Item item) throws ItemResolverException {
        if (storage.exists(item)) {
            throw new ItemResolverException();
        }
        storage.store(item);
        return item;
    }

    public Item getItem(String path) throws ItemResolverException {
        Item result = storage.get(path);
        if (result == null) {
            throw new ItemResolverException();
        }
        return result;
    }

    public Item updateItem(Item item) {
        storage.store(item);
        return item;
    }

}
