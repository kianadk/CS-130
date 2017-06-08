package edu.ucla.cs130.shwipe.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preferences {

    public ArrayList<String> category_preferences;
    public ArrayList<String> brand_preferences;
    public int min;
    public int max;

    public Preferences() {
        category_preferences = new ArrayList<String>();
        brand_preferences = new ArrayList<String>();
        min = 0;
        max = 10000000;
    }

    public void add(String category, String brand, String minP, String maxP){
        List<String> categories = Arrays.asList(category.split("\\s*,\\s*"));
        category_preferences.clear();
        for (String c : categories) {
            if (!category_preferences.contains(c)) {
                if (c.equals("men") || c.equals("women") || c.equals("kids"))
                    category_preferences.add(c);
            }
        }

        List<String> brands = Arrays.asList(brand.split("\\s*,\\s*"));
        brand_preferences.clear();
        for (String b : brands) {
            if (!brand_preferences.contains(b)) {
                brand_preferences.add(b);
            }
        }

        if (!minP.equals(""))
            min = Integer.parseInt(minP);
        if (!maxP.equals(""))
            max = Integer.parseInt(maxP);
    }
}
