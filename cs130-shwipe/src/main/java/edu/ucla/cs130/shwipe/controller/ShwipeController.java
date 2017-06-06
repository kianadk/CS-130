package edu.ucla.cs130.shwipe.controller;

import edu.ucla.cs130.shwipe.model.ProductResponse;
import edu.ucla.cs130.shwipe.model.ImageResponse;
import edu.ucla.cs130.shwipe.model.BrandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by kianakavoosi on 5/5/17.
 */
@Controller
public class ShwipeController {

    static final int LIKE_INDEX = 0;
    static final int DISLIKE_INDEX = 1;

    private HashMap<String, int[]> productData = new HashMap<String, int[]>();
    //private HashMap<String, > users = new HashMap<String, >();
    private List<String> category_preferences = new ArrayList<String>();
    private List<String> brand_preferences = new ArrayList<String>();
    private int minPrice = -1;
    private int maxPrice = -1;
    private int category_index = 0;
    private int brand_index = 0;

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

    // when a user logs in, this will try to find the user's profile that is saved
    // and return it, so that the user's activity is saved between sessions.
    @RequestMapping("/home")
    public String home(@RequestParam(name = "id") String id,
                       Map<String, Object> context) {
        return "index";
//        if (!users.containsKey(id)){
//            users.put(id, new UserData());
//        }
//        return users.get(id);
    }

    @RequestMapping(value="/fbId", produces="text/plain")
    @ResponseBody
    public String fbId(Map<String, Object> context){ return fbId; }

    @RequestMapping(value="/getLikes", produces="text/plain")
    @ResponseBody
    public String getLikes(@RequestParam(name = "productId") String productId){
        return productData.containsKey(productId) ? Integer.toString(productData.get(productId)[LIKE_INDEX]) : "0";
    }

    @RequestMapping(value="/getDislikes", produces="text/plain")
    @ResponseBody
    public String getDislikes(@RequestParam(name = "productId") String productId){
        return productData.containsKey(productId) ? Integer.toString(productData.get(productId)[DISLIKE_INDEX]) : "0";
    }

    @RequestMapping(value="/proxy", produces="Application/json")
    @ResponseBody
    public ProductResponse proxy(@RequestParam(name = "offset") int offset) {
        Long cid;
        int categories_size = category_preferences.size();
        if (categories_size == 0)
            cid = 100001755L;
        else {
            if (category_preferences.get(category_index).equals("men"))
                cid = 10150000L;
            else if (category_preferences.get(category_index).equals("women"))
                cid = 10110000L;
            else
                cid = 100001755L;
            category_index++;
            category_index %= categories_size;
        }
        offset %=250;

        Long brand_id;
        int brands_size = brand_preferences.size();
        if (brands_size == 0)
            brand_id = -1L;
        else {
            if (brand_preferences.get(brand_index).equals(""))
                brand_id = -1L;
            else
                brand_id = Long.parseLong(brand_preferences.get(brand_index), 10);
            brand_index++;
            brand_index %= brands_size;
        }

        ProductResponse response;
        String url = createCategoryInfoRequestUrl(cid, brand_id, offset);
        response = restTemplate.getForEntity(url, ProductResponse.class).getBody();
        String imageQuery = shortenSearch(response.getProductTitle());
        String imageUrl = createImageUrl(imageQuery);
        ImageResponse imageResponse = restTemplate.getForEntity(imageUrl, ImageResponse.class).getBody();
        response.replaceImages(imageResponse.getImages());
        System.out.println(url);
        System.out.println(response);
        return response;
    }


    @RequestMapping("/addData")
    @ResponseBody
    public void addData(@RequestParam(name = "productId") String productId,
                        @RequestParam(name = "index") int index){
        if(productData.containsKey(productId)){
            productData.get(productId)[index]++;
        }
        else{
            int[] data = new int[2];
            data[0] = 0;
            data[1] = 0;
            data[index]++;
            productData.put(productId, data);
        }
    }

    @RequestMapping("/addPreferences")
    @ResponseBody
    public void addPreferences(@RequestParam(name = "category") String category,
                               @RequestParam(name = "brand") String brand,
                               @RequestParam(name = "minPrice") String minP,
                               @RequestParam(name = "maxPrice") String maxP){
        List<String> categories = Arrays.asList(category.split("\\s*,\\s*"));
        category_preferences.clear();
        for (String c : categories) {
            if (!category_preferences.contains(c)) {
                category_preferences.add(c);
            }
        }
        List<String> brands = Arrays.asList(brand.split("\\s*,\\s*"));
        brand_preferences.clear();
        for (String b : brands) {
            if (!brand_preferences.contains(b)) {
                brand_preferences.add(b);
            }
        }
        if (!minP.equals(""))
            minPrice = Integer.parseInt(minP);
        if (!maxP.equals(""))
            maxPrice = Integer.parseInt(maxP);
    }

    @RequestMapping(value="/brand", produces="Application/json")
    @ResponseBody
    public BrandResponse brand(@RequestParam(name = "keyword") String keyword) {
        BrandResponse response;
        String url = createBrandInfoRequestUrl(keyword);
        response = restTemplate.getForEntity(url, BrandResponse.class).getBody();
        return response;
    }

    private String createProductInfoRequestUrl(Long productId) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/product?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&productId=" + productId +
                "&productIdType=SZOID";
        return url;
    }

    private String createCategoryInfoRequestUrl(Long productId, Long brandId, int start) {
        String url;
        url = "http://catalog.bizrate.com/services/catalog/v1/api/product?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&categoryId=" + productId +
                "&start=" + start + "&format=json&results=1";
        if (brandId != -1L)
            url = url + "&brandId=" + brandId;
        if (minPrice != -1L)
            url = url + "&minPrice=" + minPrice;
        if (maxPrice != -1L)
            url = url + "&maxPrice=" + maxPrice;
        //System.out.println(url);
        return url;
    }

    private String createImageUrl(String product){
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="
                + flickrKey + "&text=" + product + "&media=photos&per_page=5&format=json&nojsoncallback=?";
        return url;
    }

    private String createBrandInfoRequestUrl(String keyword) {
        String url = "http://catalog.bizrate.com/services/catalog/v1/api/brands?apiKey="
                + apiKey + "&publisherId=" + publisherId + "&results=512&format=json&keyword=" + keyword;
        return url;
    }

    // Since many products names are very long and unlikely to yield any results,
    // the name is shortened to the first 3 words, which should provide enough information
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
