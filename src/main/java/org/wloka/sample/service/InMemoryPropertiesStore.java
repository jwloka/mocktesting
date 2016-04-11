package org.wloka.sample.service;


import java.util.HashMap;
import java.util.Map;

import org.wloka.sample.data.Properties;


public class InMemoryPropertiesStore implements IPropertiesStore {

    private final Map<String, Properties> data;

    public InMemoryPropertiesStore() {
        this.data = new HashMap<>();
    }

    @Override
    public Properties load(String path) throws DataAccessException {
        if (data.containsKey(path)) {
            return data.get(path);
        }
        throw new DataAccessException();
    }

    @Override
    public InMemoryPropertiesStore save(String path, Properties props) throws DataAccessException {
        if (data.containsKey(path)) {
            throw new DataAccessException();
        }
        data.put(path, props);
        return this;
    }

    @Override
    public InMemoryPropertiesStore remove(String path) {
        data.remove(path);
        return this;
    }
}
