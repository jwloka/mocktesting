package org.wloka.sample.data;


import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Item {
    private final String path;

    private Properties properties;

    public Item(String path) throws InvalidDataException {
        if (StringUtils.isBlank(path)) {
            throw new InvalidDataException();
        }
        this.path = path;
    }

    public Item setProperties(Properties props) throws InvalidDataException {
        if (props == null) {
            throw new InvalidDataException();
        }
        this.properties = props;
        return this;
    }
}
