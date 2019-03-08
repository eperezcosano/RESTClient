package edu.upc.dsa.restclient;

import com.google.gson.annotations.SerializedName;

public class Comment {

    private int id;
    @SerializedName("body")
    private String text;
    @SerializedName("postId")
    private int author;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getAuthor() {
        return author;
    }
}
