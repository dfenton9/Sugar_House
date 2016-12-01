/**
 * Script to check for valid form inputs. Checks if fields are blank or if correct combinations of hikes/durations were chosen.
 */
function inputValidation(){

	var loginId = document.getElementById('loginId').value;
	var email = document.getElementById('email').value;
	var password = document.getElementById('password').value;
	
	//Ensure all fields are entered for login

	if(loginId == "" || email == "" || password == ""){
		alert("No fields can be left blank.");
		return false;
	}

        return true;
    
}
