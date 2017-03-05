package com.kruchkov.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by kruchkov on 08.02.17.
 */
public abstract class AbstractJdbcDao {

    private BasicDataSource dataSource;
    protected Connection connection;

    private BasicDataSource getDataSource() throws MyDaoException {
        if (dataSource == null) {

            BasicDataSource ds = new BasicDataSource();
            InputStream is = null;
            try {
                is = getClass().getResourceAsStream("/db.properties");
                Properties props = new Properties();
                props.load(is);

                ds.setUrl(props.getProperty("host"));
                ds.setUsername(props.getProperty("user"));
                ds.setPassword(props.getProperty("pass"));

                ds.setMinIdle(5);
                ds.setMaxIdle(10);
                ds.setMaxOpenPreparedStatements(100);

            } catch (IOException e) {
                throw new MyDaoException("IO error has occurred", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        throw new MyDaoException("Close error has occurred", e);
                    }
                }
            }
            dataSource = ds;
        }
        return dataSource;
    }

    public Connection createConnection() throws MyDaoException {
        try {
            BasicDataSource dataSource = getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new MyDaoException("Connection error", e);
        }
        return connection;
    }

}
