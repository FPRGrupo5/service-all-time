package com.service.myapplication.models;

public class RatingModel {
    private String uid;
    private int value;
    private String comment;

    public RatingModel(String uid, int value) {
        this.uid = uid;
        this.value = value;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
