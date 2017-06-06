package edu.ucla.cs130.shwipe.model;

import java.util.List;
import java.util.ArrayList;

public class UserData {
    //preferences, like/dislike data per user

    ArrayList<String> preferences;
    ArrayList<String> likes;
    ArrayList<String> dislikes;

    public UserData(){
        preferences = new ArrayList<String>();
        likes = new ArrayList<String>();
        dislikes = new ArrayList<String>();
    }
}