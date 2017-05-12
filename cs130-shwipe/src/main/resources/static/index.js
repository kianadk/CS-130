

function getNewShoe(){
    fetch("/proxy")
    .then(response => {
        response.json().then(data => {
            document.getElementById("currentShoe").innerHTML = data.offers.offer[0].manufacturer;
            console.log(data);
        });
    });
}