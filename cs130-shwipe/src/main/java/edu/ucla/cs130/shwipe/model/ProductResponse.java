package edu.ucla.cs130.shwipe.model;

import java.util.List;

/**
 * Created by aditya2506, acorhodzic, brandonpon96, kianadk, nmockovciak on 08/05/17.
 */
public class ProductResponse {
    public Products products;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "products=" + products +
                '}';
    }

    public static class Products {
        public List<Product> product;
        public Long includedResults;
        public Long totalResults;

        @Override
        public String toString() {
            return "Products{" +
                    "product=" + product +
                    ", totalResults=" + totalResults +
                    ", includedResults=" + includedResults +
                    '}';
        }
    }

    public static class Product {
        public Long id;
        //public Long merchantId;
        //public Long categoryId;
        public String title;
        public String description;
        public String manufacturer;
        public Url url;
        public Images images;

        // Add new attributes as required

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", description='" + description + '\'' +
                    ", url='" + url + '\'' +
                    ", image='" + images + '\'' +
                    '}';
        }
    }

    public static class Url{
        public String value;

        @Override
        public String toString(){
            return "Url{" +
                    "view='" + value + '\'' +
                    '}';
        }
    }

    public static class Images{
        public List<Image> image;

        @Override
        public String toString(){
            return "Images{" +
                    "image='" + image + '\'' +
                    '}';
        }
    }

    public static class Image{
        public String value;

        @Override
        public String toString(){
            return "Image{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }
}

