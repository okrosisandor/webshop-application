const inputValues = document.querySelectorAll('.input-group input');
const inputs = document.querySelectorAll('.input-group');

const password1 = document.querySelector("#password");
const password2 = document.querySelector("#password2");

for (let i = 0; i < inputValues.length; i++) {
    inputValues[i].addEventListener('blur', showIcon);
}

window.addEventListener('DOMContentLoaded', isErrorShown);
password1.addEventListener("blur", validatePassword);
password2.addEventListener("blur", validatePassword2);

function isErrorShown(){
	const errorMessages = document.querySelectorAll('p.error-message');
	for (let i = 0; i < errorMessages.length; i++) {
		const inputField = errorMessages[i].parentElement.firstElementChild;
		const errorIcon = errorMessages[i].parentElement.lastElementChild;
		const checkIcon = errorMessages[i].parentElement.lastElementChild.previousElementSibling;
		
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		inputField.style.border = '2px solid red';
	}
}

function showIcon(e){
	const inputValue = e.target.value;
	const targetId = e.target.id;
	const errorIcon = e.target.parentElement.lastElementChild;
	const checkIcon = e.target.parentElement.lastElementChild.previousElementSibling;
	
	if(targetId == 'username' && (inputValue.length < 5 || inputValue.length > 15)){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}else if(targetId == 'email' && inputValue.length < 5){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}
	else if((targetId == 'password'  && inputValue.length < 6) || (targetId == 'password2'  && inputValue.length < 6)){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}
	else if(inputValue.length < 1){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}else{
		const errorText = e.target.nextElementSibling;
		const classes = errorText.classList.value;

		if(classes.localeCompare("error-message") == 0){
			errorText.style.display = 'none';
		}
		
		errorIcon.style.display = 'none';
		checkIcon.style.display = 'block';
		e.target.style.border = '2px solid green';

	}
}

function validatePassword(){

	const pass1Value = password1.value;
	const pass2Value = password2.value;
	
	const pass1errorIcon = password1.parentElement.lastElementChild;
	const pass1checkIcon = password1.parentElement.lastElementChild.previousElementSibling;
	const pass2errorIcon = password2.parentElement.lastElementChild;
	const pass2checkIcon = password2.parentElement.lastElementChild.previousElementSibling;
	const pass1errorMessage = password1.nextElementSibling;
	const pass2errorMessage = password2.nextElementSibling;
	
	if(!pass2Value.length == 0 && !pass1Value.localeCompare(pass2Value) == 0){
		pass1checkIcon.style.display = 'none';
		pass1errorIcon.style.display = 'block';
		password1.style.border = '2px solid red';
		pass1errorMessage.style.display = 'block';
		pass1errorMessage.innerHTML = 'Passwords must match.';
		
		pass2checkIcon.style.display = 'none';
		pass2errorIcon.style.display = 'block';
		password2.style.border = '2px solid red';
		pass2errorMessage.style.display = 'block';
		pass2errorMessage.innerHTML = 'Passwords must match.';
	}else if(pass2Value.length > 5 && pass1Value.localeCompare(pass2Value) == 0){
		pass1checkIcon.style.display = 'block';
		pass1errorIcon.style.display = 'none';
		password1.style.border = '2px solid green';
		pass1errorMessage.style.display = 'none';
		
		pass2checkIcon.style.display = 'block';
		pass2errorIcon.style.display = 'none';
		password2.style.border = '2px solid green';
		pass2errorMessage.style.display = 'none';
	}
}

function validatePassword2(){
	const pass1Value = password1.value;
	const pass2Value = password2.value;
	
	const pass1errorIcon = password1.parentElement.lastElementChild;
	const pass1checkIcon = password1.parentElement.lastElementChild.previousElementSibling;
	const pass2errorIcon = password2.parentElement.lastElementChild;
	const pass2checkIcon = password2.parentElement.lastElementChild.previousElementSibling;
	const pass1errorMessage = password1.nextElementSibling;
	const pass2errorMessage = password2.nextElementSibling;
	
	if(!pass1Value.length == 0 && !pass1Value.localeCompare(pass2Value) == 0){
		pass1checkIcon.style.display = 'none';
		pass1errorIcon.style.display = 'block';
		password1.style.border = '2px solid red';
		pass1errorMessage.style.display = 'block';
		pass1errorMessage.innerHTML = 'Passwords must match.';
		
		pass2checkIcon.style.display = 'none';
		pass2errorIcon.style.display = 'block';
		password2.style.border = '2px solid red';
		pass2errorMessage.style.display = 'block';
		pass2errorMessage.innerHTML = 'Passwords must match.';
	}else if(pass1Value.length > 5 && pass1Value.localeCompare(pass2Value) == 0){
		pass1checkIcon.style.display = 'block';
		pass1errorIcon.style.display = 'none';
		password1.style.border = '2px solid green';
		pass1errorMessage.style.display = 'none';
		
		pass2checkIcon.style.display = 'block';
		pass2errorIcon.style.display = 'none';
		password2.style.border = '2px solid green';
		pass2errorMessage.style.display = 'none';
	}
}

