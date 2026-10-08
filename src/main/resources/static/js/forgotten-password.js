const incorrectPassword = document.querySelector('.incorrect-password');
const inputField = document.querySelector('.input-group input');
const inputIcon = document.querySelector('.input-group .input-icon');

window.addEventListener('DOMContentLoaded', showPasswordInfo);

function showPasswordInfo(){
	const text = incorrectPassword.innerText;
	
	if(text.length > 0){
		length = text.length;
		inputIcon.style.top = "41px";
		inputField.style.border = '2px solid red';
	}
	
}