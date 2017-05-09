package edu.ucla.cs130.shwipe.controller;

import edu.ucla.cs130.shwipe.model.MerchantsResponse;
import edu.ucla.cs130.shwipe.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by kianakavoosi on 5/5/17.
 */
@Controller
public class ShwipeController {
    @Autowired
    RestTemplate restTemplate;

    @Value("${api.key}")
    String apiKey;

    @Value("${publisher.id}")
    Long publisherId;

    @RequestMapping("/")
    public String catalog(Map<String, Object> context) {
        MerchantsResponse response;
        String url = createMerchantInfoRequestUrl(401L);
        response = restTemplate.getForEntity(url, MerchantsResponse.class).getBody();
        context.put("message", response);
        return "index";
    }

    @RequestMapping("/product") // This is only temporary
    public String product(Map<String, Object> context) {
        ProductResponse response;
        String url = createProductInfoRequestUrl(7313752673L);
        response = restTemplate.getForEntity(url, ProductResponse.class).getBody();
        context.put("message", response);
        return "index";
    }

    private String createMerchantInfoRequestUrl(Long merchantId) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/merchantinfo?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&merchantId=" + merchantId;
        return url;
    }

    private String createProductInfoRequestUrl(Long productId) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/product?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&productId=" + productId +
                "&productIdType=SZOID";
        return url;
    }
}
