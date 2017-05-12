

function getNewShoe(){
    fetch("/proxy")
    .then(response => {
        response.json().then(data => {
            var imageURL = data.products.product[0].images.image[3].value;
            var imageNode = document.getElementById("currentShoe").firstElementChild;
            imageNode.setAttribute("src", imageURL);
        });
    });
}