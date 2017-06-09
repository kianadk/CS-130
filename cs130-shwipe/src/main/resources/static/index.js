var offset = 0;
var gender = "women";
var curData = "http://www.runnersworld.com/sites/runnersworld.com/files/styles/slideshow-desktop/public/nike_free_rn_distance_m_400.jpg?itok=lvNFjcGt";
var count = "-";
var currItem = 10;
var maxItems = 10;
var itemCache = [];
var itemLoop = 0;
var currPic = 0;
var likesEnabled = false;
var male = true;
var female = true;
var kids = true;
var brandArr = [];

const LIKE_INDEX = 0;
const DISLIKE_INDEX = 1;


function addEnterFunctionality() {
    document.getElementById("brandInput")
    .addEventListener("keyup", function(event) {
    event.preventDefault();
    if (event.keyCode == 13) {
        document.getElementById("brandEnter").click();
    }
    });
}

function loginCallback(response){
    if(response.status === "connected"){
        console.log("logged in now");
    }
    else{
        console.log("did not log in");
    }
}

function logout(){
    FB.getLoginStatus(function(response) {
            if (response.status === 'connected') {
                FB.logout(function(response) {
                    window.location.assign("/");
                });
            }
            else{
                console.log("in the else");
                window.location.assign("/");
            }
        });

}

(function loadFbSdk(){
    fetch("/fbId")
    .then(response => {
        response.text().then(data => {
          window.fbAsyncInit = function() {
            FB.init({
              appId            : data,
              autoLogAppEvents : true,
              xfbml            : true,
              version          : 'v2.9'
            });
            FB.AppEvents.logPageView();
          };

          (function(d, s, id){
             var js, fjs = d.getElementsByTagName(s)[0];
             if (d.getElementById(id)) {return;}
             js = d.createElement(s); js.id = id;
             js.src = "//connect.facebook.net/en_US/sdk.js";
             fjs.parentNode.insertBefore(js, fjs);
           }(document, 'script', 'facebook-jssdk'));
        });
    });
})()

function getNewShoe(){
    //fetch("/proxy?category=" + gender + "&offset=" + offset++)
    console.log("cur: " + currItem + " max: " + maxItems);
    if (currItem == maxItems) {
        var imageNode = document.getElementById("currentShoe").firstElementChild;
        likesEnabled = false;
        imageNode.setAttribute("src", "https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif");
        fetch("/proxy?offset=" + offset++ + "&userId=" + getId())
        .then(response => {
            response.json().then(data => {
                itemCache = data.products.product;
                maxItems = itemCache.length;
                currItem = 0;
                newShoeHelper();
            });
        });
    } else {
        newShoeHelper();
    }
}

document.onkeydown = function handleKeyPress(e){
    if(e.keyCode == 37){
        dislike();
    }
    else if(e.keyCode == 39){
        like();
    }
    else if(e.keyCode == 40){
        getNewPic();
    }
}

function newShoeHelper(){
    curData  = itemCache[currItem];
    currItem++;
    console.log("num images: " + curData.images.image.length);
    var imageURL = curData.images.image[0].value;
    itemLoop = curData.images.image.length;
    currPic = 0;
    console.log(imageURL);
    var imageNode = document.getElementById("currentShoe").firstElementChild;

    var oldMorePics = document.getElementById("morepics");

    if (oldMorePics) {
        //remove button
        oldMorePics.remove();
    }

    if (curData.images.image[1]) {
        var morePics = document.createElement("button");
        morePics.setAttribute("type", "button");
        morePics.setAttribute("onclick", "getNewPic()");
        morePics.setAttribute("id", "morepics");
        morePics.innerHTML = "MORE PICS"

        document.getElementById("morePicsButtonDiv").appendChild(morePics);
    }

    imageNode.setAttribute("src", imageURL);
    likesEnabled = true;
}

function like(){
    if(likesEnabled){
        getLikes();
    }
}

function getId(){
    idIndex = window.location.search.indexOf("id=");
    userId = window.location.search.substring(idIndex + 3);
    return userId;
}

function dislike(){
    if(likesEnabled){
        recordDislike();
        getNewShoe();
    }
}

function recordDislike(){
    fetch("/addDislikeData?productId=" + curData.id + "&userId=" + getId());
}

function recordLike(){
    fetch("/addLikeData?productId=" + curData.id + LIKE_INDEX + "&userId=" + getId() +
    "&name=" + curData.title + "&link=" + curData.url.value + "&picture=" + curData.images.image[0].value
    + "&description=" + curData.description);
}

function getLikes(){
    fetch("/getLikes?productId=" + curData.id)
    .then(response => {
        response.text().then(data => {
            addToLikeList((data * 1) + 1);
            recordLike();
            getNewShoe();
        });
    });
}

function addToLikeList(like_count){
    var title = document.createElement("p");
    var likes = document.createElement("p");
    var titleText = document.createTextNode(curData.title);
    var likeText = document.createTextNode(like_count + " likes");
    title.appendChild(titleText);
    likes.appendChild(likeText);

    var link = document.createElement("a");
    link.setAttribute("href", curData.url.value);
    link.appendChild(title);

    var newImg = document.createElement("img");
    newImg.setAttribute("src", document.getElementById("currentShoe").firstElementChild.getAttribute("src"));
    newImg.setAttribute("class", "likedProdImg");

    var textBox = document.createElement("div");
    textBox.setAttribute("class", "textBox");
    textBox.appendChild(link);

    var likesData = document.createElement("div");
    likesData.setAttribute("class", "data");
    likesData.appendChild(likes);
    var likedProd = document.createElement("div");
    likedProd.setAttribute("class", "likedProd");
    likedProd.appendChild(newImg);
    likedProd.appendChild(textBox);
    likedProd.appendChild(likesData);


    var expand = document.createElement("div");
    expand.setAttribute("id", "expand");
    var see = document.createElement("p");
    var t2 = document.createTextNode(">> see description");
    see.appendChild(t2);
    expand.appendChild(see);

    var desc = document.createElement("p");
    var t3 = document.createTextNode(curData.description);
    desc.appendChild(t3);
    desc.setAttribute("id", "description");
    expand.appendChild(desc);
    likedProd.appendChild(expand);

    document.getElementById("likeList").insertBefore(likedProd, document.getElementById("likeList").firstElementChild);

}

function getNewPic(){
    var imageURL;
    var imageNode = document.getElementById("currentShoe").firstElementChild;

    currPic++;
    imageURL = curData.images.image[currPic % itemLoop].value;
    imageNode.setAttribute("src", imageURL);
}

function setMale() {
    male = document.getElementById("genderMale").checked;
}

function setFemale() {
    female = document.getElementById("genderFemale").checked;
}

function setKids() {
    kids = document.getElementById("genderKids").checked;
}

function brandAdd() {
    var newBrand = document.getElementById("brandInput").value;

    fetch("/brand?keyword=" + newBrand)
        .then(response => {
            response.json().then(data => {
                if(data) {
                    brandArr.push(data.brands.brand[0].id);
                    document.getElementById("brandInput").setAttribute("placeholder","Added");
                    document.getElementById("brandInput").value = "";

                }
                else {
                    document.getElementById("brandInput").setAttribute("placeholder","Not Found");
                    document.getElementById("brandInput").value = "";
                }

            });
    });
}


function setPreferences() {
   var query = "/addPreferences?category=";
   var first = true;

   if (male) {
        query = query.concat("men");
        first = false;
   }
   if (female) {
        if (!first){ query = query.concat(","); }
        query = query.concat("women");
        first = false;
   }
   if (kids) {
        if (!first){ query = query.concat(","); }
        query = query.concat("kids");
   }

   var min = document.getElementById("minPrice").value;
   var max = document.getElementById("maxPrice").value;

   if(!min || !max) {
        min = 0;
        max = 100000;
   }

   query = query.concat("&minPrice=" + min + "&maxPrice=" + max);


   query = query.concat("&brand=")

   first = true;
   var brandId = "";

   while (brandArr.length > 0) {
        brandId = brandArr.pop();
        if (!first) { query = query.concat(","); }
        query = query.concat(brandId);
        first = false;
   }

   query = query.concat("&userId=" + getId());
   console.log(query);

   fetch(query);
   offset = 0;

   currItem = maxItems;
   getNewShoe();
}