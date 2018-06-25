package com.example.jolysylvain.squadup;

import java.util.Date;

public class Message {

    private String sender = "";
    private String receveur = "";
    private String message = "";
    private Date send_at;
    private String _id;

    public Message(String sender, String receveur, String message, Date send_at, String _id) {
        this.sender = sender;
        this.receveur = receveur;
        this.message = message;
        this.send_at = send_at;
        this._id = _id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceveur() {
        return receveur;
    }

    public void setReceveur(String receveur) {
        this.receveur = receveur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSend_at() {
        return send_at;
    }

    public void setSend_at(Date send_at) {
        this.send_at = send_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
