package edu.ucla.cs130.shwipe.model;

import java.util.List;

/**
 * Created by aditya2506, acorhodzic, brandonpon96, kianadk, nmockovciak on 08/05/17.
 */
public class ProductResponse {
    public Offers offers;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "offers=" + offers +
                '}';
    }

    public static class Offers {
        public List<Offer> offer;
        public Long includedResults;
        public Long totalResults;

        @Override
        public String toString() {
            return "Offers{" +
                    "offer=" + offer +
                    ", totalResults=" + totalResults +
                    ", includedResults=" + includedResults +
                    '}';
        }
    }

    public static class Offer {
        public Long id;
        //public Long merchantId;
        //public Long categoryId;
        public String title;
        public String description;
        public String manufacturer;

        // Add new attributes as required

        @Override
        public String toString() {
            return "Offer{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}

