
async function loadData() {

    const popup = new Notification();

    const response = await fetch("loadData");
    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            document.getElementById("all-item-count").innerHTML=json.allProductCount;
            console.log(json);
            LoadOptions("brand", json.brandList, "name");
            LoadOptions("condition", json.qualityList, "value");
            LoadOptions("color", json.colorList, "value");
            LoadOptions("storage", json.storageList, "value");
        } else {
            popup.error({
                message: "Somthing went wrong Please try agian leater"
            });
        }
    } else {
        popup.error({
            message: "Somthing went wrong Please try agian leater"
        });
    }
}


function LoadOptions(prefix, datalist, property) {
    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";

    datalist.forEach(item => {
        let li_clone = li.cloneNode(true);
        if (prefix == "color") {
            li_clone.style.borderColor = "black";
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = item[property];
        } else {
            li_clone.querySelector("#" + prefix + "-a").innerHTML = item[property];
        }
        options.appendChild(li_clone);
    });

    const all_li = document.querySelectorAll("#" + prefix + "-options li");

    all_li.forEach(list => {
        list.addEventListener("click", function () {
            all_li.forEach(y => {
                y.classList.remove("chosen");
            });
            this.classList.add("chosen");
        });
    });
}