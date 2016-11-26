/**
 * Script to check for valid form inputs. Checks if fields are blank or if correct combinations of hikes/durations were chosen.
 */
function inputValidation(){

	var loginId = document.getElementById('loginId').value;
	var email = document.getElementById('email').value;
	var password = document.getElementById('password').value;
	var confirmPassword = document.getElementById('confirmPassword').value;
	var confirmEmail = document.getElementById('confirmEmail').value;

	
	//Ensure all fields are entered for registration
	if (loginId == "" || email == "" || password == ""|| confirmEmail == "" || confirmPassword == "") {
		 alert("No fields can be left blank.");
	     return false;
	} 
	
	//Ensure both registration emails entered are the same
	if (email != confirmEmail){
		 alert("Emails do not match.");
	     return false;
	}
	
	//Ensure both registration passwords entered are the same
	if (password != confirmPassword){
		 alert("Passwords do not match.");
	     return false;
	}

        return true;
    
}  
