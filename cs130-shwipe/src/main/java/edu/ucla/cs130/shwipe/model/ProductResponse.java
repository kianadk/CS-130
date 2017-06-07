package edu.ucla.cs130.shwipe.model;

import java.util.List;
import java.util.ArrayList;

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

    public int getSize() {
        return products.product.size();
    }

    public String getProductTitle(int i){
        return products.product.get(i).title;
    }

    public void replaceImages(List<String> imgUrl, int i){
        products.product.get(i).images.replaceImages(imgUrl);
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
        public Long categoryId;
        public String title;
        public String description;
        public String manufacturer;
        public Url url;
        public Images images;
        public Price price;


        // Add new attributes as required

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", price='" + price + '\'' +
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

    public static class Price{
        public String value;

        @Override
        public String toString(){
            return "Price{" +
                    "value='" + value + '\'' +
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
        unit test:
        more stock than flickr: all flickr photos should be used
        same stock and flickr: all stock replaced by flickr
        less stock than flickr: replace all stock, add remaining flickr
         */
        public void replaceImages(List<String> imgUrl){
            int oldSize = image.size();
            ArrayList<Image> newList = new ArrayList();
            if (oldSize != 0) {
                if (oldSize < 4)
                    newList.add(image.get(oldSize - 1));
                else
                    newList.add(image.get(3));
            }
            int newSize = imgUrl.size();
            System.out.println("number of flickr images: " + newSize);
            for(int i = 0; i < newSize; i++){
                newList.add(new Image(imgUrl.get(i)));
            }
            image = newList;

//            int counter = 0;
//            int size = (newSize <= oldSize) ? newSize : oldSize;
//            for (int i = 0; i < size; i++) {
//                image.get(i).value = imgUrl.get(i);
//            }
//            if (size < newSize){
//                for(; size < newSize; size++){
//                    image.add(new Image(imgUrl.get(size)));
//                }
//            }
        }
    }

}

