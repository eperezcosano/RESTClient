package edu.upc.dsa.restclient;

public class Track {
    String id;
    String title;
    String singer;

    public Track(String title, String singer) {
        this.title = title;
        this.singer = singer;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }
}
