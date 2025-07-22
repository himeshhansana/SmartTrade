
async function loadData() {
    console.log("single product js done.");

    const searchParam = new URLSearchParams(window.location.search);
    if (searchParam.has("id")) {
        const productId = searchParam.get("id");
        const response = await fetch("LoadSingleProduct?id=" + productId);
        if (response.ok) {
            const json = await response.json();
            if (json.status) {
                console.log(json);

                //single product view images
                document.getElementById("image1").src = "product-images\\" + json.product.id + "\\image1.png";
                document.getElementById("image2").src = "product-images\\" + json.product.id + "\\image2.png";
                document.getElementById("image3").src = "product-images\\" + json.product.id + "\\image3.png";

                document.getElementById("thumb-imge1").src = "product-images\\" + json.product.id + "\\image1.png";
                document.getElementById("thumb-imge2").src = "product-images\\" + json.product.id + "\\image2.png";
                document.getElementById("thumb-imge3").src = "product-images\\" + json.product.id + "\\image3.png";
                //single product view images

                document.getElementById("product-title").innerHTML = json.product.title;
                document.getElementById("published-on").innerHTML = json.product.created_at;
                document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {minimumFractionDigits: 2}).format(json.product.price);
                document.getElementById("brand-name").innerHTML = json.product.model.brand.name;
                document.getElementById("model-name").innerHTML = json.product.model.name;
                document.getElementById("product-quality").innerHTML = json.product.quality.value;
                document.getElementById("product-stock").innerHTML = json.product.qty;

                //product color
                document.getElementById("color-border").style.borderColor = "black";
                document.getElementById("color-background").style.backgroundColor = json.product.color.value;
                document.getElementById("product-storage").innerHTML = json.product.storage.value;
                document.getElementById("product-description").innerHTML = json.product.description;


                //add to cart main button
                const addToCartMain = document.getElementById("add-to-cart-main");
                addToCartMain.addEventListener(
                        "click", (e) => {
                    addToCart(json.product.id, document.getElementById("add-to-cart-qty").value);
                    e.preventDefault();
                });
                //add to cart main button



                //similer-product
                let similer_product_main = document.getElementById("smiler-product-main");
                let productHtml = document.getElementById("similer-product");
                similer_product_main.innerHTML = "";
                json.productList.forEach(item => {
                    let productCloneHtml = productHtml.cloneNode(true);
                    productCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id=" + item.id;
                    productCloneHtml.querySelector("#similer-product-image").src = "product-images\\" + item.id + "\\image1.png";
                    productCloneHtml.querySelector("#similer-product-add-to-cart").addEventListener(
                            "click", (e) => {
                        addToCart(item.id, 1);

                        e.preventDefault();
                    });
                    productCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id=" + item.id;
                    productCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
                    productCloneHtml.querySelector("#similer-product-storage").innerHTML = item.storage.value;
                    productCloneHtml.querySelector("#similer-product-price").innerHTML = "Rs." + new Intl.NumberFormat(
                            "en-US",
                            {minimumFractionDigits: 2})
                            .format(item.price);
                    productCloneHtml.querySelector("#similer-product-color-border").style.borderColor = "black";
                    productCloneHtml.querySelector("#similer-product-color-background").style.backgroundColor = item.color.value;
                    similer_product_main.appendChild(productCloneHtml);

//                    let similerProductDesign = `<div class="slick-single-layout" id="similer-product">
//                            <div class="axil-product">
//                                <div class="thumbnail">
//                                    <a href="${'single-product.html?id=' + item.id}" id="similer-product-a1">
//                                        <img src="assets/images/product/product-01.png" id="similer-product-image" alt="Product Images">
//                                    </a>
//
//                                    <div class="product-hover-action">
//                                        <ul class="cart-action">
//                                            <li class="wishlist"><a href="#"><i class="far fa-heart"></i></a></li>
//                                            <li class="select-option"><a href="#" id="similer-product-add-to-cart" onclick="addToCart(${item.id},1)">Add to Cart</a></li>
//                                            <li class="quickview"><a href="${'sig'}" id="similer-product-a2" ><i class="far fa-eye"></i></a></li>
//                                        </ul>
//                                    </div>
//                                </div>
//                                <div class="product-content">
//                                    <div class="inner">
//                                        <h5 class="title" id="similer-product-title"><a href="#">${item.title}</a></h5>
//                                        <p class="b2 mb--10" id="similer-product-storage">${item.storage.value}</p>
//                                        <div class="product-price-variant">
//                                            <span class="price current-price" id="similer-product-price">${new Intl.NumberFormat(
//                            "en-US",
//                            {minimumFractionDigits: 2})
//                            .format(item.price)}</span>
//                                        </div>
//                                        <div class="color-variant-wrapper">
//                                            <ul class="color-variant">
//                                                <li class="color-extra-01 active">
//                                                    <!-- color-border and color-background -->
//                                                    <span id="similer-product-color-border">
//                                                        <span class="color" id="similer-product-color-background"></span></span> 
//                                                </li>
//                                            </ul>
//                                        </div>
//                                    </div>
//                                </div>
//                            </div>
//                        </div>`;
//                    similer_product_main.innerHTML += similerProductDesign;

                });
                //similer-product end

                $('.recent-product-activation').slick({
                    infinite: true,
                    slidesToShow: 4,
                    slidesToScroll: 4,
                    arrows: true,
                    dots: false,
                    prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
                    nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
                    responsive: [{
                            breakpoint: 1199,
                            settings: {
                                slidesToShow: 3,
                                slidesToScroll: 3
                            }
                        },
                        {
                            breakpoint: 991,
                            settings: {
                                slidesToShow: 2,
                                slidesToScroll: 2
                            }
                        },
                        {
                            breakpoint: 479,
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 1
                            }
                        }
                    ]
                });


            } else {
                window.location = "index.html";
            }
        } else {
            window.location = "index.html";

        }
    }

}



function addToCart(productId, qty) {
    console.log(productId + "" + qty);
}