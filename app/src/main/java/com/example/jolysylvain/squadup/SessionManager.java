package com.example.jolysylvain.squadup;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    public Context current_context;

    private static final String PREF_NAME = "SquadUp";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        current_context = context;
        preferences = getCurrent_context().getSharedPreferences(PREF_NAME, PRIVATE_MODE); //instanciate preferences
    }


    /**
     * Only for dev, remove sharedPreferences SquadUp, (can be done via AVD manager on device option)
     */

    public String clearSharedPreferences(){
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();

        return "SharedPreferences clear with success";
    }

    /**
     *
     * @param token the jwt token return by the API (must be a valid jwt token), and he will stored into the sharedPreferences SquadUp for the authentification on each ativities
     */
    public void storeToken(String token){
        editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    /**
     *
     * @return token if SquadUp preference key token is not empty else return R.string.ERROR_TOKEN
     */
    public String getToken(){
        return preferences.getString("token", current_context.getString(R.string.ERROR_TOKEN));
    }

    public Context getCurrent_context() {
        return current_context;
    }

    public void setCurrent_context(Context current_context) {
        this.current_context = current_context;
    }

}
