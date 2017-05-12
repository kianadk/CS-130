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
        public Images images;
        public Url url;


        // Add new attributes as required

        @Override
        public String toString() {
            return "Offer{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", description='" + description + '\'' +
                   // ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class Url {
        public String value;

        @Override
        public String toString() {
            return "Url{" +
                    "url=" + value +
                    '}';
        }
    }

    public static class Images {
        public List<Image> image;

        @Override
        public String toString() {
            return "Images{" +
                    "image=" + image +
                    '}';
        }
    }

    public static class Image {
        public String value;

        @Override
        public String toString() {
            return "Image{" +
                    "image=" + value +
                    '}';
        }
    }

}

