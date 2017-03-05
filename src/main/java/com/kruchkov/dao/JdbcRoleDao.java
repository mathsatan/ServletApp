package com.kruchkov.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by kruchkov on 08.02.17.
 */
public class JdbcRoleDao extends AbstractJdbcDao implements RoleDao {

    public void create(Role role) throws MyDaoException {
        if (role == null) {
            throw new NullPointerException("role is null");
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("INSERT INTO my_rols (name, role_id) VALUES (?, ?)");
            stmt.setString(1, role.getName());
            Long id = role.getId() != null ? role.getId() : UUID.randomUUID().getMostSignificantBits();
            stmt.setLong(2, id);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new MyDaoException("Rollback fails", ex);
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Statement connection fails", ex);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Close connection fails", ex);
            }
        }
    }

    public void update(Role role) throws MyDaoException {
        if (role == null) {
            throw new MyDaoException("role is null");
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Long id = role.getId() != null ? role.getId() : UUID.randomUUID().getMostSignificantBits();
            conn = this.createConnection();
            stmt = conn.prepareStatement("UPDATE my_rols SET name = ? WHERE role_id = ?");
            stmt.setString(1, role.getName());
            stmt.setLong(2, id);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new MyDaoException("Rollback fails", ex);
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Statement connection fails", ex);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Close connection fails", ex);
            }
        }
    }

    public void remove(Role role) throws MyDaoException {
        if (role == null) {
            throw new MyDaoException("role is null");
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("DELETE FROM my_roles WHERE role_id = ?");
            stmt.setLong(1, role.getId());
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new MyDaoException("Rollback fails", ex);
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Close connection fails", ex);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Statement connection fails", ex);
            }
        }
    }

    public Role findByName(String name) throws MyDaoException {
        if (name == null) {
            throw new MyDaoException("name is null");
        }
        Role role = new Role();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("SELECT * FROM my_rols WHERE name = ?");
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }
            role.setId(result.getLong("role_id"));
            role.setName(result.getString("name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Statement connection fails", ex);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new MyDaoException("Close connection fails", ex);
            }
        }
        return role;
    }
}