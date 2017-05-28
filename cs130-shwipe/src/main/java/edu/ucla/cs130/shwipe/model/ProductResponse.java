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

    public String getProductTitle(){
        if (products.product.size() > 0)
            return products.product.get(0).title;
        else return null;
    }

    public void replaceImages(List<String> imgUrl){
        products.product.get(0).images.replaceImages(imgUrl);
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

    public static class Image{
        public String value;

        public Image(String value){
            this.value = value;
        }

        public Image(){
            super();
        }

        @Override
        public String toString(){
            return "Image{" +
                    "value='" + value + '\'' +
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

        /*
        if there are none, default to those provided by connexity
        replace stock with however many from flickr, 5 max
        if less than # of ones from stock, then just keep those
         */
        public void replaceImages(List<String> imgUrl){
            int oldSize = image.size();
            int newSize = imgUrl.size();
            System.out.println("old size: " + oldSize + "\nnew size: " + newSize);
            int counter = 0;
            int size = (newSize <= oldSize) ? newSize : oldSize;
            for (int i = 0; i < size; i++) {
                image.get(i).value = imgUrl.get(i);
            }
            if (size < newSize){
                for(; size < newSize; size++){
                    image.add(new Image(imgUrl.get(size)));
                }
            }
        }
    }

}

