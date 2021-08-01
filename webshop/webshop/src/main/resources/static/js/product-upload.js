const productName = document.querySelector("#productName");
const price = document.querySelector("#price");
const description = document.querySelector("#description");
const details = document.querySelectorAll('.validate');
const button = document.querySelector("#upload-button");

for(let i = 0; i < details.length; i++){
	details[i].addEventListener('blur', validateDetail);
}

function validateDetail(e){
	const value = e.target.value;
	const theId = e.target.id;
	let requiredLength = 0;
	
	const checkIcon = e.target.nextElementSibling;
	const errorIcon = e.target.nextElementSibling.nextElementSibling;
	const errorText = e.target.previousElementSibling;
	
	errorText.innerHTML = "Field is required";
	errorIcon.style.top = "50%";
	
	if(theId.localeCompare("productName") == 0){
		requiredLength = 5;
	}else if(theId.localeCompare("description") == 0){
		requiredLength = 1;
	}

	if((!theId.localeCompare("price") == 0 && value.length >= requiredLength) || (theId.localeCompare("price") == 0 && value > 0)){
			e.target.style.border = "2px solid green";
			errorIcon.style.display = "none";
			checkIcon.style.display = "block";
			errorText.style.display = "none";
			e.target.style.marginTop = "8px";
	}else{
		e.target.style.border = "2px solid red";
		checkIcon.style.display = "none";
		errorIcon.style.display = "block";
		errorText.style.display = "block";
		e.target.style.marginTop = "0px";
		
		if(theId.localeCompare("productName") == 0){
			errorText.innerHTML = "Length must be 5 or more";
		}
		
		if(theId.localeCompare("description") == 0){
			errorIcon.style.top = "45%";
		}
	}
	
	if(productName.value.length >= requiredLength && description.value.length > 0 && price.value > 0){
		button.style.color = 'white';
		button.disabled = false;
		button.style.cursor = 'pointer';
	}else{
		button.style.color = '#222';
		button.disabled = true;
		button.style.cursor = 'default';
	}
}