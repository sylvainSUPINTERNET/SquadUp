package com.example.jolysylvain.squadup;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User {

    private String description = "";
    private String email = "";
    private String name = "";
    private String nbProfiles = "";


    public User(String email, String description, String name, String nbProfiles) {
        this.email = email;
        this.description = description;
        this.name = name;
        this.nbProfiles = nbProfiles;
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

    public String getNbProfiles() {
        return nbProfiles;
    }

    public void setNbProfiles(String nbProfiles) {
        this.nbProfiles = nbProfiles;
    }




}
