var modelList;

async function loadProductData() {
    const response = await fetch("LoadProductData");


    if (response.ok) {
        const json = await response.json();
        console.log(json);
        if (json.status) {

            //load brands
            loadSelect("brand", json.brandList, "name");


            //load models
            modelList = json.modelList;
            // loadSelect("model",json.modelList,"name");

            //load storage

            loadSelect("storage", json.storageList, "value");

            //load color
            loadSelect("color", json.colorList, "value");

            //load qualtiy
            loadSelect("condition", json.qualityList, "value");


        } else {
            document.getElementById("message").innerHTML = "Unable to get product data ! Please try again later";

        }


    } else {
        document.getElementById("message").innerHTML = "Unable to get product data ! Please try again later";
    }
}

function loadSelect(selectId, list, property) {
    const select = document.getElementById(selectId);

    list.forEach(item => {
        const option = document.createElement("option");
        option.value = item.id;
        option.innerHTML = item[property];
        select.appendChild(option);
    });

}

function loadModels() {

    const brandId = document.getElementById("brand").value;
    const modelSelect = document.getElementById("model");
    modelSelect.length = 1;

    modelList.forEach(item => {
        if (item.brand.id == brandId) {
            const option = document.createElement("option");
            option.value = item.id;
            option.innerHTML = item.name;
            modelSelect.appendChild(option);


        }
    });

}

async function saveProuct() {

    const brandId = document.getElementById("brand").value;
    const modelId = document.getElementById("model").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const storageId = document.getElementById("storage").value;
    const colorId = document.getElementById("color").value;
    const conditionId = document.getElementById("condition").value;
    const price = document.getElementById("price").value;
    const qty = document.getElementById("qty").value;

    const image1 = document.getElementById("img1").files[0];
    const image2 = document.getElementById("img2").files[0];
    const image3 = document.getElementById("img3").files[0];

    const form = new FormData();
    form.append("brandId", brandId);
    form.append("modelId", modelId);
    form.append("title", title);
    form.append("description", description);
    form.append("storageId", storageId);
    form.append("colorId", colorId);
    form.append("conditionId", conditionId);
    form.append("price", price);
    form.append("qty", qty);
    form.append("image1", image1);
    form.append("image2", image2);
    form.append("image3", image3);

    const response = await fetch(
            "SaveProduct",
            {
                method: "POST",
                body: form
            }
    );


const popup = Notification();

    if (response.ok) { // success
        const json = await response.json();
        if (json.status) {
        } else {// when status false
            if (json.message === "Please sign in!") {
                window.location = "sing-in.html";
            } else {
                popup.error(
                        {
                            message: json.message
                        }
                );
            }
        }
    } else {
    }
}

