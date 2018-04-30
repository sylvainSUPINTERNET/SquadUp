package com.example.jolysylvain.squadup;

import android.support.v7.app.AppCompatActivity;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Auth_checker extends AppCompatActivity {

    protected String firstname = "";
    protected String lastname = "";
    protected String email = "";
    protected String password = "";
    protected String passwordConfirmed = "";


    public Auth_checker(String firstname, String lastname, String email, String password, String passwordConfirmed) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.passwordConfirmed = passwordConfirmed;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    /*

        TODO checker for login / register et pour le display dans le dialog


    public Map<String, String> checkFields(){
        Boolean error = false;
        if(firstname){
            error = true;
        }else{
            // error still false
        }

        Map<String, String> errors = new HashMap<String, String>();
        errors.put("firstname", "error");


        return errors;
    }

    */
}
