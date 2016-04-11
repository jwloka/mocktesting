package org.wloka.sample.service;


import org.wloka.sample.data.InvalidDataException;
import org.wloka.sample.data.Item;
import org.wloka.sample.service.IPropertiesStore.DataAccessException;


public class DB {
    private static DB singleton = null;

    public static DB init() {
        synchronized (DB.class) {
            if (singleton == null) {
                singleton = new DB();
            }
            return singleton;
        }
    }

    private IPropertiesStore data;

    protected DB() {
        this(new InMemoryPropertiesStore());
    }

    protected DB(IPropertiesStore store) {
        this.data = store;
    }

    public Item get(String path) {
        try {
            return new Item(path).setProperties(data.load(path));
        } catch (InvalidDataException | DataAccessException ex) {
            return null;
        }
    }

    public DB store(Item item) {
        if (item != null) {
            try {
                data.save(item.getPath(), item.getProperties());
            } catch (DataAccessException ex) {
                // falls through
            }
        }
        return this;
    }

    public DB delete(String path) {
        if (path != null) {
            try {
                data.remove(path);
            } catch (DataAccessException ex) {
                // falls through
            }
        }
        return this;
    }

    public boolean exists(Item item) {
        try {
            return data.load(item.getPath()) != null;
        } catch (DataAccessException ex) {
            return false;
        }
    }
}
