package edu.ucla.cs130.shwipe.model;

/**
 * Created by kianakavoosi on 6/6/17.
 */
public class LikedProduct {
    String linkToBuy;
    String productPicture;
    String productName;
    String productDescription;
    String productId;
    String numLikes;

    public LikedProduct(String linkToBuy,
                        String productPicture,
                        String productName,
                        String productDescription,
                        String productId){
        this.linkToBuy = linkToBuy;
        this.productPicture = productPicture;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productId = productId;
        this.numLikes = "0";
    }

    public String getId(){
        return this.productId;
    }

    public void setLikes(String likes){
        this.numLikes = likes;
    }

}
