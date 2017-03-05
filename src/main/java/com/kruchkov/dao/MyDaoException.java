package com.kruchkov.dao;

/**
 * Created by root on 05.03.17.
 */
public class MyDaoException extends Exception {
    MyDaoException() {
        super();
    }

    MyDaoException(String msg) {
        super(msg);
    }

    MyDaoException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
