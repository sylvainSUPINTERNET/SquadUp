package com.example.jolysylvain.squadup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Serializable {
    Context context;
    String current_token;

    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;

        SessionManager sessionManager = new SessionManager(context);

        //Log.d("HOME TOKEN ->",getSharedPreferences("SquadUp", MODE_PRIVATE).getString("token", ""));
        //TODO display en fonction de si on est connecté ou pas et set dans le current_token
        if(sessionManager.getToken().equals(getString(R.string.ERROR_TOKEN))){
            Log.d("HOME TOKEN", "PAS DE TOKEN POUR LE MOMENT");
            current_token = getString(R.string.ERROR_TOKEN);
        }else{
            Log.d("HOME TOKEN ?",sessionManager.getToken());
            current_token = sessionManager.getToken();
        }

        setContentView(R.layout.activity_main);
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



        //TODO display la liste uniquement si le token est OK
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        UserAdapter.OnItemClickListener mListener = new UserAdapter.OnItemClickListener(){
                @Override public void onItemClick(User user){
                    //todo open new window from right with all info + profiles list

                    Intent intent = new Intent(context, UserDetail.class);
                    intent.putExtra("user_name", user.getName().toString());
                    //intent.putExtra("user_email", user.getEmail().toString());
                    intent.putExtra("user_description", user.getDescription().toString());
                    //intent.putExtra("user_role", user.getRole().toString());
                    //todo profiles apres (ARRAY)

                    Log.d("TESTON",user.getRole());
                    //TODO BUG ICI EMAIL ROLE EMPTY
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                }
        };
        mAdapter = new UserAdapter(usersList, mListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        prepareUserData();

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
        getMenuInflater().inflate(R.menu.main, menu);
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

      if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, AuthRegister.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);

      } else if (id == R.id.nav_slideshow) {
          Intent intent = new Intent(this, AuthLogin.class);
          startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareUserData() {

        //todo get data from API maintenant et faire une loop pour add chaque sur sdans la liste et setter l'adapter sur cette list

        /*
        User user1 = new User("test@aeza.fr","DESCRIPTION", "ROLE_USER", "SYLVAIN");
        usersList.add(user1);
        User user2 = new User("test@aezaR.fr","R", "ROLE_USER", "JLY");
        usersList.add(user2);

        mAdapter.notifyDataSetChanged();
        */

        //SI il y un token
        if(!current_token.equals(getString(R.string.ERROR_TOKEN))){
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-access-token", current_token);

            client.post("http://10.0.2.2:1337/api/user/list", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    try {
                        JSONObject json = new JSONObject(
                                new String(responseBody));
                        System.out.println(json);

                        String email = "";
                        String name = "";
                        String role = "";
                        String description = "";

                        JSONArray users = json.getJSONArray("users");

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject row = users.getJSONObject(i);
                            //System.out.println(row.getString("name"));

                            name = row.getString("name");
                                 /*
                            email = row.getString("email");
                            */
                            description = row.getString("description");
                            //role = row.getString("role");

                            User user = new User(email,description, role, name);
                            usersList.add(user);
                            mAdapter.notifyDataSetChanged();
                        }

                        //TODO : IMPORTANT API rajouter un champs true false error sur le res.json pour gérer les erreurs ....
                        //TODO TOUJOURS COTE API il manque l'email -_- ROLE AUSSI et même profile -_- qu'il faudra pouvoir voir en cliquant sur le nom du gars

                        /*
                        if(json.getBoolean("error") == false){

                        }else{

                        }
                         */


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }else{
            // pas de token valid donc pas de list
            //TODO message sur la home page comme quoi vous êtes pas co et donc pas de list visible
        }




    }
}
