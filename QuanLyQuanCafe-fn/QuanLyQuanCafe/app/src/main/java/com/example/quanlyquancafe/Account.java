package com.example.quanlyquancafe;

public class Account {

    private long id;
    private String username;
    private String email;

    private int role;

    public Account(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Account(String username, String email, int role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Account(long id, String username, String fullName, int role) {
        this.id = id;
        this.username = username;
        this.email = fullName;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
