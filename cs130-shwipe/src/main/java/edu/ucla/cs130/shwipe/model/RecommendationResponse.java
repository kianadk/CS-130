package edu.ucla.cs130.shwipe.model;

public class RecommendationResponse {

    public static class UserProfile {
        public Integer male = 5;
        public Integer female = 5;
        public String minPrice = "00";
        public String maxPrice = "10000";

        public void increaseMale() {
            if (male < 10) {
                male++;
                female--;
            }
            return;
        }

        public void increaseFemale() {
            if (female < 10) {
                female++;
                male--;
            }
            return;
        }

        public void changeMin(String newMin) {
            minPrice = newMin;
            return;
        }

        public void changeMax(String newMax) {
            maxPrice = newMax;
            return;
        }

    }

}
