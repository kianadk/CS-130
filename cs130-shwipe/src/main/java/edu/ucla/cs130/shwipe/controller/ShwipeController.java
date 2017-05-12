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

    @RequestMapping("/") // This is only temporary
    public String product(Map<String, Object> context) {
        return "index";
    }

    @RequestMapping(value="/proxy", produces="Application/json")
    @ResponseBody
    public ProductResponse proxy(@RequestParam(name = "category") String category,
                                 @RequestParam(name = "offset") int offset) {
        Long cid;
        if (category.equals("men"))
            cid = 10150000L;
        else if (category.equals("women"))
            cid = 10110000L;
        else
            cid = 100001755L;
        offset %=250;
        ProductResponse response;
        String url = createCategoryInfoRequestUrl(cid, offset);
        response = restTemplate.getForEntity(url, ProductResponse.class).getBody();
        System.out.println(response);
        return response;
    }

    private String createProductInfoRequestUrl(Long productId) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/product?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&productId=" + productId +
                "&productIdType=SZOID";
        return url;
    }

    private String createCategoryInfoRequestUrl(Long productId, int start) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/product?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&categoryId=" + productId +
                "&start=" + start + "&format=json&results=1";
        return url;
    }
}
