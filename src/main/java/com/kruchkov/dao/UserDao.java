package com.kruchkov.dao;

import java.util.List;

/**
 * Created by kruchkov on 08.02.17.
 */
public interface UserDao {
    public void create(User user) throws MyDaoException;
    public void update(User user) throws MyDaoException;
    public void remove(User user) throws MyDaoException;
    public List<User> findAll() throws MyDaoException;
    public User findByLogin(String login) throws MyDaoException;
    public User findByEmail(String email) throws MyDaoException;
}
