package edu.ucla.cs130.shwipe.controller;

import edu.ucla.cs130.shwipe.model.MerchantsResponse;
import edu.ucla.cs130.shwipe.model.ProductResponse;
import edu.ucla.cs130.shwipe.model.ImageResponse;
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

    @Value("${flickr.key}")
    String flickrKey;

    @Value("${fb.id}")
    String fbId;

    @RequestMapping("/")
    public String index(Map<String, Object> context) {
        return "login";
    }

    @RequestMapping("/home")
    public String home(Map<String, Object> context) {
        return "index";
    }

    @RequestMapping(value="/fbId", produces="text/plain")
    @ResponseBody
    public String fbId(Map<String, Object> context){ return fbId; }

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
        String imageQuery = shortenSearch(response.getProductTitle());
        String imageUrl = createImageUrl(imageQuery);
        ImageResponse imageResponse = restTemplate.getForEntity(imageUrl, ImageResponse.class).getBody();
        response.replaceImages(imageResponse.getImages());
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

    private String createImageUrl(String product){
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="
                + flickrKey + "&text=" + product + "&media=photos&per_page=5&format=json&nojsoncallback=?";
        return url;
    }

    private String shortenSearch(String query){
        String[] split = query.split("\\s+");
        String formattedQuery = split[0];
        if (split.length <= 3)
            for(int i = 1; i < split.length; i++)
                formattedQuery += "+" + split[i];
        else formattedQuery = formattedQuery + "+" + split[1] + "+" + split[2];
        return formattedQuery;
    }
}
