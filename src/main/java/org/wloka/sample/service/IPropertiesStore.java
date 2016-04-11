package org.wloka.sample.service;


import org.wloka.sample.data.Properties;


public interface IPropertiesStore {
    Properties load(String path) throws DataAccessException;

    IPropertiesStore save(String path, Properties item) throws DataAccessException;

    IPropertiesStore remove(String path) throws DataAccessException;

    class DataAccessException extends Exception {}
}
