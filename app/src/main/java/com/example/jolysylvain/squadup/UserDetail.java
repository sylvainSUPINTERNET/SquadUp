package com.example.jolysylvain.squadup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
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
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import cz.msebera.android.httpclient.Header;

public class UserDetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //TODO envoyer via intent.Extra bla bla les profiles (refaire dans l'objet User un champ en plus de type Array avec tous les profile)
    Bundle extras;
    String user_name;
    String user_email;
    String user_role;
    String user_description;
    String nbProfiles;

    TextView title;
    TextView description;
    TextView email;
    TextView profiles_nb_view;


    Context context;

    String current_token;

    String receveur;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getIntent().getExtras();
        context = this;


        SessionManager sessionManager = new SessionManager(context);
        if(sessionManager.getToken().equals(getString(R.string.ERROR_TOKEN))){
            Log.d("HOME TOKEN", "PAS DE TOKEN POUR LE MOMENT");
            current_token = getString(R.string.ERROR_TOKEN);
        }else{
            Log.d("HOME TOKEN ?",sessionManager.getToken());
            current_token = sessionManager.getToken();
        }

        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (extras != null) {
            user_name = extras.getString("user_name");
            //user_email= extras.getString("user_email");
            user_description = extras.getString("user_description");
            nbProfiles = extras.getString("user_nb_profiles");

            //user_role = extras.getString("user_role");

            //todo ERROR ici email est empty le role aussi

            title = findViewById(R.id.title);
            email = findViewById(R.id.email);
            description = findViewById(R.id.description);
            profiles_nb_view = findViewById(R.id.nb_profiles);

            title.setText(user_name);
            description.setText(user_description);

            profiles_nb_view.setText(nbProfiles);

            

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                    builder.setTitle("Ecrivez un message à " + user_name);

                    // Set up the input
                    final EditText input = new EditText(context);
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setId(R.id.message);
                    builder.setView(input);

                    builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            /* Pas besoin car API va déjà parser son current_token
                            JWT parsedJWT = new JWT(current_token);
                            Claim subscriptionMetaData = parsedJWT.getClaim("name"); // parse and get key name form token sent
                            user_name_from_token = subscriptionMetaData.asString();
                            */

                            //post new message

                            message = input.getText().toString();
                            receveur = title.getText().toString();

                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("Content-Type", "application/x-www-form-urlencoded");
                            client.addHeader("x-access-token", current_token);
                            final RequestParams body = new RequestParams();

                            body.put("message", message);
                            body.put("receveur",receveur);

                            client.post(getString(R.string.DOMAIN)+""+getString(R.string.API_PORT)+"/api/message/add", body, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                                    try {
                                        JSONObject json = new JSONObject(
                                                new String(responseBody));
                                        System.out.println(json);
                                        System.out.print("status code" + statusCode);


                                        if(json.getBoolean("error") == false){
                                            DialogManager success = new DialogManager("Votre message a bien été envoyé !", "Message envoyé", context);
                                            success.generateDialog().show();
                                        }else{
                                            DialogManager success = new DialogManager(json.getString("message"), "Erreur lors de l'envoi de votre message", context);
                                            success.generateDialog().show();
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
                    });

                    //cancel l'envoit du message
                    builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });

        }else{
            Log.d("ERROR EXTRAS", "EXTRA IS NULL");
        }


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
        getMenuInflater().inflate(R.menu.user_detail, menu);
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setTitle("Ecrivez un message à " + user_name);

            // Set up the input
            final EditText input = new EditText(context);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setId(R.id.message);
            builder.setView(input);

            builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                            /* Pas besoin car API va déjà parser son current_token
                            JWT parsedJWT = new JWT(current_token);
                            Claim subscriptionMetaData = parsedJWT.getClaim("name"); // parse and get key name form token sent
                            user_name_from_token = subscriptionMetaData.asString();
                            */

                    //post new message

                    message = input.getText().toString();
                    receveur = title.getText().toString();

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("Content-Type", "application/x-www-form-urlencoded");
                    client.addHeader("x-access-token", current_token);
                    final RequestParams body = new RequestParams();

                    body.put("message", message);
                    body.put("receveur",receveur);

                    client.post(getString(R.string.DOMAIN)+""+getString(R.string.API_PORT)+"/api/message/add", body, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                            try {
                                JSONObject json = new JSONObject(
                                        new String(responseBody));
                                System.out.println(json);
                                System.out.print("status code" + statusCode);


                                if(json.getBoolean("error") == false){
                                    DialogManager success = new DialogManager("Votre message a bien été envoyé !", "Message envoyé", context);
                                    success.generateDialog().show();
                                }else{
                                    DialogManager success = new DialogManager(json.getString("message"), "Erreur lors de l'envoi de votre message", context);
                                    success.generateDialog().show();
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
            });

            //cancel l'envoit du message
            builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                }
            });

            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_registration) {
            Intent intent = new Intent(this, AuthRegister.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.menu_login) {
            Intent intent = new Intent(this, AuthLogin.class);
            startActivity(intent);
        } else if (id == R.id.menu_messages) {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_users) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_webapp) {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.webapp_url)));
            startActivity(intent);

        } else if (id == R.id.menu_email) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.nav_header_title)});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact SquadUp");
            intent.putExtra(Intent.EXTRA_TEXT, "Veuillez nous indiquer votre requête . . .");
            startActivity(Intent.createChooser(intent, ""));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
