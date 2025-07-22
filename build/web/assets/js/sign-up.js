async function signUp() {
//    console.log("ok");

    //use  jquery
//    const firstName = $("#firstName").val();
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;



    const user = {

        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password

    };
    
    const userJson = JSON.stringify(user);
    
    const response = await fetch(
        "SignUp",
        {
            method: "POST",
            body:userJson,
            headers:{
                "Content-Type":"application/json"
            }
        }
    );
    
    
    
    
    if(response.ok){ // success (status code 200 )
        
        const json = await response.json();
        
        if(json.status){
            document.getElementById("message").className="text-successs";
            document.getElementById("message").innerHTML=json.message;
            window.location = "verify-account.html";
        }else{
            
            document.getElementById("message").innerHTML=json.message;
            //custom message
           // console.log(json.message);
           
           
           
        }
        
    }else{
        document.getElementById("message").innerHTML="Registration Failed. Please Try Again";
    }

}