

function getNewShoe(){
    fetch("/proxy?category=kids&offset=0")
    .then(response => {
        response.json().then(data => {
            document.getElementById("currentShoe").innerHTML = data.products.product[0].manufacturer;
            console.log(data);
        });
    });
}