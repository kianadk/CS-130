var offset = 0;
var gender = "women";
var curData = "http://www.runnersworld.com/sites/runnersworld.com/files/styles/slideshow-desktop/public/nike_free_rn_distance_m_400.jpg?itok=lvNFjcGt";

function loginCallback(response){
    if(response.status === "connected"){
        console.log("logged in now");
    }
    else{
        console.log("did not log in");
    }
}

function loadFbSdk(){
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
}

function getNewShoe(){
    fetch("/proxy?category=" + gender + "&offset=" + offset++)
    .then(response => {
        response.json().then(data => {
            curData  = data.products.product[0];
            var imageURL = curData.images.image[3].value;
            var imageNode = document.getElementById("currentShoe").firstElementChild;
            imageNode.setAttribute("src", imageURL);
        });
    });
}

function addToLikes(){
    var newPara = document.createElement("p");
    var t = document.createTextNode(curData.title);
    newPara.appendChild(t);
    var newImg = document.createElement("img");
    newImg.setAttribute("src", curData.images.image[3].value);
    var newDiv1 = document.createElement("div");
    newDiv1.setAttribute("class", "textBox");
    newDiv1.appendChild(newPara);
    var newDiv2 = document.createElement("div");
    newDiv2.setAttribute("class", "likedProd");
    newDiv2.appendChild(newImg);
    newDiv2.appendChild(newDiv1);

    var link = document.createElement("a");
    link.setAttribute("href", curData.url.view);
    link.appendChild(newDiv2);

    document.getElementById("likeList").appendChild(link);
}