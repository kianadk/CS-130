package edu.ucla.cs130.shwipe.model;

import java.util.List;

/**
 * Created by aditya2506, acorhodzic, brandonpon96, kianadk, nmockovciak on 08/05/17.
 */
public class BrandResponse {
    public Brands brands;

    @Override
    public String toString() {
        return "BrandResponse{" +
                "brands=" + brands +
                '}';
    }

    public static class Brands {
        public List<Brand> brand;
        public Long includedResults;
        public Long totalResults;

        @Override
        public String toString() {
            return "Brands{" +
                    "brand=" + brand +
                    ", totalResults=" + totalResults +
                    ", includedResults=" + includedResults +
                    '}';
        }
    }

    public static class Brand {
        public Long id;
        public String name;
        public Long productCount;
        // Add new attributes as required

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", productCount='" + productCount + '\'' +
                    '}';
        }
    }

}

