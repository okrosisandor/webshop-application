const incorrectLogin = document.querySelector('.incorrect-login');
const loggedOut = document.querySelector('.logged-out');
const loginForm = document.querySelector('#login-form');
const inputValues = document.querySelectorAll('.input-group input');

const forgottenPasswordButton = document.querySelector('#forgotten-password');
const forgottenPasswordForm = document.querySelector('#forgotten-password-form');
const theLoginForm = document.querySelector('#login-form');
const emailSent = document.querySelector('.email-sent');
const enterEmail = document.querySelector('.enter-email');


document.body.addEventListener('mousemove', removeIncorrent);
document.body.addEventListener('mouseover', removeLoggedOut);

forgottenPasswordButton.addEventListener('click', showForgottenPasswordForm);
window.addEventListener('DOMContentLoaded', showEmailInfo);


for (let i = 0; i < inputValues.length; i++) {
    inputValues[i].addEventListener('blur', showIcon);
}

function removeIncorrent(){
	setTimeout(function(){
		if(incorrectLogin != null){
  			incorrectLogin.style.display = 'none';
		}
	}, 3000)
}

function removeLoggedOut(){
	setTimeout(function(){
		if(loggedOut != null){
	  		loggedOut.style.display = 'none';
		}
	}, 5000)
}

function showIcon(e){
	const inputValue = e.target.value;
	const targetId = e.target.id;
	const errorIcon = e.target.parentElement.lastElementChild;
	const checkIcon = e.target.parentElement.lastElementChild.previousElementSibling;
	
	if(targetId == 'userName' && inputValue.length < 5){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}
	else if(targetId == 'password'  && inputValue.length < 6){
		checkIcon.style.display = 'none';
		errorIcon.style.display = 'block';
		e.target.style.border = '2px solid red';
	}
	else{
		errorIcon.style.display = 'none';
		checkIcon.style.display = 'block';
		e.target.style.border = '2px solid green';
	}
}


function showForgottenPasswordForm(){
    forgottenPasswordForm.style.display = 'block';
    theLoginForm.style.marginTop = "4rem";
}

function showEmailInfo(){
	const text = emailSent.innerText;
	const errorText = "Email address does not exist";
	const successText = "Email has been sent to your email. Please check your inbox.";
	
	if(text.length > 0){
		length = text.length;
		forgottenPasswordForm.style.display = 'block';
		emailSent.style.display = 'block';
	}
	
	if((text.localeCompare(errorText)) == 0){
		emailSent.style.color = 'red';
		
		setTimeout(function(){
			emailSent.style.display = 'none';
		}, 3000)
	}else if((text.localeCompare(successText)) == 0){
		enterEmail.style.display = 'none';
		emailSent.style.color = 'darkgreen';
	}
	
}

