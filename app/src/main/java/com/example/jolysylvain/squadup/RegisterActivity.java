package com.example.jolysylvain.squadup;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/*
 *
 * http://loopj.com/android-async-http/
 *
 */
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;


public class RegisterActivity extends AppCompatActivity {

    EditText lastname;
    EditText email;
    EditText password;
    EditText passwordConfirmed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View v) {
        // do something when the button is clicked
        lastname = findViewById(R.id.register_lastname);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        passwordConfirmed = findViewById(R.id.register_passwordConfirmed);


        //error
        //TODO faire deux dialog en fonction du retour de checker pour voir les erreurs a faire aussi
        DialogManager dialogManager = new DialogManager("Veuillez vérifier vos champs !", "Erreur lors de l'enregistrement", this);
        dialogManager.generateDialog().show();


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

                    //TODO get token and store into Storage android

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
