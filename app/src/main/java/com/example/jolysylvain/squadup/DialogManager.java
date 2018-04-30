package com.example.jolysylvain.squadup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class DialogManager extends AppCompatActivity {

    public String DialogMessage = "";
    public String Dialogtitle = "";
    public Context context;

    public DialogManager(String msg, String title, Context context) {
        this.DialogMessage = msg;
        this.Dialogtitle = title;
        this.context = context;
    }

    public String getDialogMessage() {
        return DialogMessage;
    }

    public void setDialogMessage(String message) {
        this.DialogMessage = message;
    }

    public String getDialogtitle() {
        return Dialogtitle;
    }

    public void setDialogtitle(String title) {
        this.Dialogtitle = title;
    }

    /**
     * @return object type Dialog ( return.show() display the dialog window)
     */
    public Dialog generateDialog(){
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(this.context);

        builderDialog
                .setMessage(this.getDialogMessage())
                .setTitle(this.getDialogtitle());

        AlertDialog dialogCreated = builderDialog.create();
        return dialogCreated;
    }
}
