package com.kruchkov.dao;

/**
 * Created by kruchkov on 08.02.17.
 */
public interface RoleDao {
    public void create(Role role) throws MyDaoException;
    public void update(Role role) throws MyDaoException;
    public void remove(Role role) throws MyDaoException;
    public Role findByName(String name) throws MyDaoException;
}
