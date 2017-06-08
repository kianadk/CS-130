package edu.ucla.cs130.shwipe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya2506, acorhodzic, brandonpon96, kianadk, nmockovciak on 08/05/17.
 */
public class ProductCheckResponse {
    public Products products;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "products=" + products +
                '}';
    }

    public static class Products {
        public Long includedResults;
        public Long totalResults;

    }
}

