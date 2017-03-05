package com.kruchkov.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by kruchkov on 08.02.17.
 */
public class JdbcUserDao extends AbstractJdbcDao implements UserDao {

    private Long getRoleIdByName(String name) throws MyDaoException {
        if (name == null) {
            return null;
        }
        Long roleId = null;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("SELECT role_id, name FROM my_rols WHERE name = ?");
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            roleId = result.getLong("role_id");
        } catch (SQLException e) {
            throw new MyDaoException("Operation fails", e);
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
        return roleId;
    }

    private String getRoleNameById(Long roleId) throws MyDaoException {
        if (roleId == null) {
            return null;
        }
        String role = null;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = this.createConnection();
            stmt = connection.prepareStatement("SELECT * FROM my_rols WHERE role_id = ?");
            stmt.setLong(1, roleId);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            role = result.getString("name");
        } catch (SQLException e) {
            throw new MyDaoException("Operation fails", e);
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

    public void create(User user) throws MyDaoException {
        if (user == null) {
            throw new MyDaoException ("login is null");
        }
        Connection conn = null;
        Long id = (user.getId() == null) ? UUID.randomUUID().getMostSignificantBits() : user.getId();
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("INSERT INTO my_users  (login, pass, first_name, last_name, email, birthday, fk_role_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            java.sql.Date date = user.getBirthday();
            if (date == null) {
                throw new IllegalArgumentException("Birthday is null");
            }
            stmt.setString(6, date.toString());
            Long roleId = getRoleIdByName(user.getRole());
            Long roleId2 = (roleId == null) ? 2L : roleId;
            stmt.setLong(7, roleId2);
            stmt.setLong(8, id);
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

    public void update(User user) throws MyDaoException {
        if (user == null) {
            throw new MyDaoException("user is null");
        }
        if (user.getId() == null) {
            throw new MyDaoException("user id is null");
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = connection.prepareStatement("UPDATE my_users  SET login = ?, pass = ?, first_name = ?, last_name = ?, email = ?, birthday = ?, fk_role_id = ? WHERE user_id = ?");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            java.sql.Date date = user.getBirthday();
            if (date == null) {
                throw new IllegalArgumentException("Birthday is null");
            }
            stmt.setString(6, date.toString());
            stmt.setLong(7, getRoleIdByName(user.getRole()));
            stmt.setLong(8, user.getId());
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

    public void remove(User user) throws MyDaoException {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("user id is null");
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("DELETE FROM my_users  WHERE user_id = ?");
            stmt.setLong(1, user.getId());
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

    public List<User> findAll() throws MyDaoException {
        List<User> users = new LinkedList<User>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("SELECT user_id, login, first_name, last_name, birthday, fk_role_id, email, pass FROM my_users ");

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("user_id"));
                user.setLogin(result.getString("login"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
                user.setRole(getRoleNameById(result.getLong("fk_role_id")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new MyDaoException("Operation fails", e);
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
        return users;
    }

    public User findByLogin(String login) throws MyDaoException {
        if (login == null) {
            throw new MyDaoException("login is null");
        }
        User user = new User();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("SELECT user_id, login, first_name, last_name, birthday, fk_role_id, email, pass FROM my_users  WHERE login = ?");
            stmt.setString(1, login);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }
            user.setId(result.getLong("user_id"));
            user.setLogin(result.getString("login"));
            user.setFirstName(result.getString("first_name"));
            user.setEmail(result.getString("email"));
            user.setPassword(result.getString("pass"));
            user.setLastName(result.getString("last_name"));
            user.setBirthday(result.getDate("birthday"));
            Long role = result.getLong("fk_role_id");
            String str = getRoleNameById(role);
            user.setRole(str);

        } catch (SQLException e) {
            throw new MyDaoException("Operation fails", e);
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
        return user;
    }

    public User findByEmail(String email) throws MyDaoException {
        if (email == null) {
            throw new MyDaoException("login is null");
        }
        User user = new User();
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = this.createConnection();
            stmt = conn.prepareStatement("SELECT user_id, login, first_name, last_name, birthday, fk_role_id, email, pass FROM my_users  WHERE email = ?");
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }
            user.setId(result.getLong("user_id"));
            user.setLogin(result.getString("login"));
            user.setFirstName(result.getString("first_name"));
            user.setLastName(result.getString("last_name"));
            user.setBirthday(result.getDate("birthday"));
            user.setRole(getRoleNameById(result.getLong("fk_role_id")));
        } catch (SQLException e) {
            throw new MyDaoException("Operation fails", e);
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
        return user;
    }
}
