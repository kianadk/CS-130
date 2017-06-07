package edu.ucla.cs130.shwipe.model;


import edu.ucla.cs130.shwipe.model.LikedProduct;
import java.util.List;
import java.util.ArrayList;

public class UserData {
    //preferences, like/dislike data per user

    ArrayList<String> preferences;
    ArrayList<LikedProduct> likes;
    ArrayList<String> dislikes;

    public UserData(){
        preferences = new ArrayList<String>();
        likes = new ArrayList<LikedProduct>();
        dislikes = new ArrayList<String>();
    }

    public ArrayList<LikedProduct> getLikes(){
        return this.likes;
    }
}