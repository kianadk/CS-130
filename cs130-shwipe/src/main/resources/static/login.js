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
            getLoginStatus();
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
})();

function getLoginStatus(){
    FB.getLoginStatus(function(response){
        if(response.status === "connected"){
            //redirect to home if logged in
            window.location.replace("home");
        }
    });
}

function loginCallback(response){
    if(response.status === "connected"){
        window.location.replace("home");
    }
}