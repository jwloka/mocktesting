package org.wloka.sample.data;


import java.util.HashMap;
import java.util.Map;


public class Properties {
    private final Map<Object, Object> data;

    public Properties() {
        this(new HashMap<>());
    }

    public Properties(Map<Object, Object> data) {
        this.data = data;
    }

    public boolean hasProperty(Object key) {
        return data.containsKey(key);
    }

    public Properties setValue(Object key, Object value) {
        if (key != null && value != null) {
            data.put(key, value);
        }
        return this;
    }

    public Object getValue(Object key) throws InvalidDataException {
        if (hasProperty(key)) {
            return data.get(key);
        }
        throw new InvalidDataException();
    }
}
