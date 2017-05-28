package edu.ucla.cs130.shwipe.model;

import java.util.List;
import java.util.ArrayList;

public class ImageResponse {
    public Photos photos;

    @Override
    public String toString() {
        return "ImageResponse: " + photos;
    }

    public List<String> getImages(){
        return photos.getImages();
    }

    public static class Photos {
        public List<Photo> photo;

        @Override
        public String toString() {
            String s = "Photos: " + photo.size();
            for(int i = 0; i < photo.size(); i++){
                s += " { " + photo.get(i) + " } ";
            }
            return s;
        }

        public List<String> getImages(){
            List<String> imgUrl = new ArrayList<String>();
            for(int i = 0; i < photo.size(); i++){
                imgUrl.add(photo.get(i).constructURL());
            }
            return imgUrl;
        }
    }

    public static class Photo {
        public String id;
        public String secret;
        public String server;
        public int farm;

        @Override
        public String toString() {
            return "url = '" + constructURL() + "'";
        }

        public String constructURL() {
            return "https://farm" + farm + ".staticflickr.com/" +
                server + "/" + id + "_" + secret + ".jpg";
        }
    }
}