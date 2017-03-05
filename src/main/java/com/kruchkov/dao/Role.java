package com.kruchkov.dao;

/**
 * Created by kruchkov on 08.02.17.
 */
public class Role {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }
}
