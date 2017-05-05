package edu.ucla.cs130.shwipe.model;

import java.util.List;

/**
 * Created by kianakavoosi on 5/5/17.
 */
public class MerchantsResponse {
    public List<Merchant> merchant;
    public Long totalMerchants;

    @Override
    public String toString() {
        return "MerchantsResponse{" +
                "merchant=" + merchant +
                ", totalMerchants=" + totalMerchants +
                '}';
    }

    public static class Merchant {
        public MerchantInfo merchantInfo;
        public Long mid;

        @Override
        public String toString() {
            return "Merchant{" +
                    "merchantInfo=" + merchantInfo +
                    ", mid=" + mid +
                    '}';
        }
    }

    public static class MerchantInfo {
        public String name;
        public String url;
        public String merchantUrl;
        public String logoUrl;
        public Long totalProductCount;
        public String countryCode;

        @Override
        public String toString() {
            return "MerchantInfo{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", merchantUrl='" + merchantUrl + '\'' +
                    ", logoUrl='" + logoUrl + '\'' +
                    ", totalProductCount=" + totalProductCount +
                    ", countryCode='" + countryCode + '\'' +
                    '}';
        }
    }
}