package com.example.jolysylvain.squadup;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AuthRegister extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    EditText lastname;
    EditText email;
    EditText password;
    EditText passwordConfirmed;

    Context context;

    String user_token;

    Account account;
    AccountManager accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
           *
           *  SET THE CONTEXT
           *
         */
        this.context = this;

        setContentView(R.layout.activity_auth_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.auth_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void register(View v) {
        // do something when the button is clicked
        lastname = findViewById(R.id.register_lastname);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        passwordConfirmed = findViewById(R.id.register_passwordConfirmed);


        //TODO move this call API is just a test
        AsyncHttpClient client = new AsyncHttpClient();

        final RequestParams body = new RequestParams();
        body.put("name", lastname.getText().toString());
        body.put("email", email.getText().toString());
        body.put("password", password.getText().toString());
        body.put("passwordConfirmed", passwordConfirmed.getText().toString());

        client.post("http://10.0.2.2:1337/api/user/register", body, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    JSONObject json = new JSONObject(
                            new String(responseBody));
                    System.out.println(json);
                    System.out.print("status code" + statusCode);

                    //TODO : IMPORTANT

                        if(json.getBoolean("error") == false){
                            DialogManager success = new DialogManager(json.getString("message"), "Register terminé", context);
                            success.generateDialog().show();

                            //TODO coté API, ajouter "email" dans l'objet userInformations (register ctrl api) et l''ajouter dans l'account etc ici
                            String user_name = json.getJSONObject("user").getString("name");
                            user_token = json.getString("token");

                            account = new Account(user_name, getString(R.string.USER_CREDENTIALS));
                            Bundle userData = new Bundle();
                            userData.putString("token",user_token);
                            userData.putString("name",user_name);

                            accountManager = AccountManager.get(context);
                            //accountManager.addAccountExplicitly(account, password.getText().toString() ,userData);
                            //Log.d("ACCOUNT created ->", accountManager.getAccountsByType("user_credentials").toString()); //store token + name (todo add email api et ici)
                            Log.d("TEST ACCOUNT", account.toString());

                            /*
                            //TODO get token and store into Storage android (AccountManager) https://developer.android.com/training/id-auth/identify
                            user_token = json.getString("token");
                            account = new Account(json.getString("name"), "user_credentials");
                            Bundle user_data = new Bundle();
                            user_data.putString("firstname", json.getJSONObject("user").getString("firstname"));
                            user_data.putString("email", json.getJSONObject("user").getString("email"));
                            user_data.putString("name", json.getJSONObject("user").getString("name"));
                            user_data.putString("token", json.getString("token"));

                            accountManager.addAccountExplicitly(account, json.getJSONObject("user").getString("pasword") ,user_data);

                            Log.d("account form manager", accountManager.getAccounts().toString());
                            //TODO redirect user vers la home
                            */

                        }else{
                            //todo -> rajouter dans le controller register de l'api dans le return json un field (error:true sur les erreurs) sinon on rentre jamais dans ce IF par contre, regarder que ca casse a rien dans le register cote app web
                            String errorMsg = "";
                            String lastnameError = json.getString("name");
                            String passwordError = json.getString("password");
                            String passwordConfirmedError = json.getString("passwordConfirmed");
                            String emailError = json.getString("email");

                            errorMsg = lastnameError + "\n" + passwordError + "\n" + passwordConfirmedError + "\n" + emailError + "\n";

                            DialogManager errorRegister = new DialogManager(errorMsg, "Erreur lors de l'enregistrement", context);
                            errorRegister.generateDialog().show();
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
