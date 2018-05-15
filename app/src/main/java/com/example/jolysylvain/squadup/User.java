package com.example.jolysylvain.squadup;

import android.content.Context;

public class User {

    private String role = "";
    private String description = "";
    private String email = "";
    private String name = "";




    public User(String email, String description, String role, String name) {
        this.email = email;
        this.description = description;
        this.role = role;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
