
async function signIn() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;



    const signIn = {
        email: email,
        password: password
    };

    const signInJson = JSON.stringify(signIn);

    const response = await fetch(
            "SignIn",
            {
                method: "POST",
                body: signInJson,
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );


    if (response.ok) {

        const json = await response.json();
        if (json.status) {
            if (json.message === "1") {

                window.location = "verify-account.html";

            } else {
                window.location = "index.html";
            }
        } else {
            document.getElementById("message").innerHTML = json.message;
        }

    } else {
        document.getElementById("message").innerHTML = "Sign In Failed. Please Try Again";

    }
}

//async function authenticateUser() {
//    try {
//        const response = await fetch("SignIn");
//
//        if (response.ok) {
//             
//            const json = await response.json();
//            
//            if (json.message === "1") {
//                window.location.href = "index.html";
//            }
//        } else {
//            console.error("Server returned error status:", response.status);
//        }
//    } catch (error) {
//        console.error("Error while authenticating user:", error);
//    }
//}

