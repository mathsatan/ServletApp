package com.kruchkov.dao;

/**
 * Created by kruchkov on 08.02.17.
 */
public class User {
    public User() {
        role = "regular_user";
    }

    private String role;
    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private java.sql.Date birthday;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws NullPointerException {
        if (login == null) {
            throw new NullPointerException();
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        String ePattern = "^(.+)@(.+)$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }
}
