package com.company;

import java.util.ArrayList;
import java.util.List;

public class movie {

    String movieID;
    String title;
    String description;
    String genre;
    String releaseDate;
    String imdbVotes;
    String imdbRating;
    List<actor> actors = new ArrayList<>();
    List<director> directors = new ArrayList<>();

    movie(String id, String title, String description, String genre, String releaseDate, String imdbVotes, String imdbRating) {
        this.movieID = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.imdbVotes = imdbVotes;
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "\'" + title + "\'" ;
    }
}

