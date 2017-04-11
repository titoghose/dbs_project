package com.example.android.requiry;

/**
 * Created by tito on 11/4/17.
 */

public class Resources {
    private String links;

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public Resources(String links) {

        this.links = links;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "links='" + links + '\'' +
                '}';
    }
}

