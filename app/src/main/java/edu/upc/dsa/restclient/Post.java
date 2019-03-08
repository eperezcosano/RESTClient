package edu.upc.dsa.restclient;

public class Post {

    private Integer id;
    private String title;

    public Post(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
