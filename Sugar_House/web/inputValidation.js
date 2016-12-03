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

function quantityValidation(ele)
{
    var data = ele.quantity.value;
    if(parseInt(data) && (data%1)===0) {
          return true;
    }else
    {
          alert("Must enter whole number value.");
          return false;
    }

}
