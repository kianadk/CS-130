package edu.ucla.cs130.shwipe.model;


import edu.ucla.cs130.shwipe.model.LikedProduct;
import java.util.List;
import java.util.ArrayList;

public class UserData {
    //preferences, like/dislike data per user

    Preferences preferences;
    ArrayList<LikedProduct> likes;
    ArrayList<String> dislikes;

    public UserData(){
        preferences = new Preferences();
        likes = new ArrayList<LikedProduct>();
        dislikes = new ArrayList<String>();
    }

    public ArrayList<LikedProduct> getLikes(){
        return this.likes;
    }
    public Preferences getPreferences(){
        return this.preferences;
    }

    public void addPreferences (String category, String brand, String minP, String maxP) {
        this.preferences.add(category, brand, minP, maxP);
    }
}