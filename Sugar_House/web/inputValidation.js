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

function confirmDelete()
{
    if(confirm("Are you sure you want to delete this item?") === true)
        return true;
    else
        return false;
}

function inventoryValidation(ele)
{
    var name = ele.name.value; //48
    var cost = ele.cost.value;
    var units = ele.quantity.value;
    var notes = ele.description.value; //512
    
    // Name Validation
    if(name === "" || name === null || cost === null || cost === "" || units === null || units === "" || notes === null || notes === "")
    {
       alert("All fields must be filled in!");
        return false; 
    }
    
    if(name.length > 48 || name.length === 0)
    {
        alert("Name must be less than 48 Characters. Currently using " + name.length);

        return false;
    }
    
    //Cost Validation
    if(cost.charAt(0) === '$' && cost.length > 1)
    {
        cost = cost.substring(1);
    }
    var regex  = /^\d+(?:\.\d{0,2})$/;
    if (!regex.test(cost))
    {
        alert("Must enter a decimal value ex: 5.99 or $4.67.");
        return false;
    }
    
    //Quantity Validation
  if(!parseInt(units) || (units%1)!==0 || units < 0 ){
        alert("Must enter whole positive number >= 0.");
        return false;
    }
    
    //Notes Validation
    if(notes.length > 512)
    {
        alert("Name must be less than 512 Characters. Currently using " + notes.length);

        return false;
    }
    
    return true;
}
