const showUserCart = document.querySelector('#show-cart');
const showPurchasedGoods = document.querySelector('#show-purchased-goods');
const showSettings = document.querySelector('#show-settings');
const userCart = document.querySelector('.user-cart');
const purchasedGoods = document.querySelector('.purchased-goods');
const settings = document.querySelector('.settings');

const opinionForms = document.querySelectorAll(".purchased-goods .info form textarea");

const changeDetailsForm = document.querySelector('.settings form')
const userDetails = document.querySelectorAll('.settings input');


showUserCart.addEventListener('click', displayUserCart);
showPurchasedGoods.addEventListener('click', displayPurchasedGoods);
showSettings.addEventListener('click', displaySettings);

for(let i = 0; i < opinionForms.length; i++){
	opinionForms[i].addEventListener('change', validateOpinionForm);
}

for(let i = 0; i < userDetails.length; i++){
	userDetails[i].addEventListener('change', validateUserDetail);
}


function validateUserDetail(e){
	const theId = e.target.id;
	const theValue = e.target.value;
	const theLength = theValue.length;
	let passes = true;
	const button = document.querySelector(".settings button");
	
	if(theLength != 0){
		if((theId.localeCompare("email") == 0 && theLength < 5) || (theId.localeCompare("password1") == 0 && theLength < 6)
		 		|| (theId.localeCompare("password2") == 0 && theLength < 6)){
			
			passes = false;
		}else if(theId.localeCompare("password1") == 0 && theLength > 5){
				const password2Value = document.querySelector('#password2').value;
			
				if(theValue.localeCompare(password2Value) != 0){
					passes = false;
				}else{
					passes = true;
				}
				
				
		}else if(theId.localeCompare("password2") == 0 && theLength > 5){
				const password1Value = document.querySelector('#password1').value;
			
				if(theValue.localeCompare(password1Value) != 0){
					passes = false;
				}else{
					passes = true;
				}
	
		}
	}else{
		const password1Value = document.querySelector('#password1').value;
		const password2Value = document.querySelector('#password2').value;
		
		if(password1Value.localeCompare(password2Value) != 0){
			passes = false;
		}else{
			passes = true;
		}
	}
	
	if(!passes){
		const inputField = e.target;
		
		button.style.backgroundColor = '#666';
		button.disabled = true;
		button.style.cursor = 'default';
		
		inputField.style.border = "1px solid red";
		
		if(theId.localeCompare("email") == 0 || (theId.localeCompare("password1") == 0 && theLength < 6) ||
			 (theId.localeCompare("password2") == 0 && theLength < 6)){
				
			if(theId.localeCompare("email") == 0){
				const emailError = document.querySelector('.email-error');
				emailError.style.display = "block";
				emailError.innerText = "Invalid email";
			}else{
				const password1ErrorMessage = document.querySelector('.password-error');
				
				const password1Input = document.querySelector('#password1');
				const password2Input = document.querySelector('#password2');
				
				password1ErrorMessage.innerText = "Length must be 6 or more";
				password1ErrorMessage.style.display = "block";
				
				password1Input.style.border = "1px solid red";
				password2Input.style.border = "1px solid red";
				
			}
		}else{
			const password1ErrorMessage = document.querySelector('.password-error')
			
			const password1Input = document.querySelector('#password1');
			const password2Input = document.querySelector('#password2');
			
			password1ErrorMessage.innerText = "Passwords must match";
			password1ErrorMessage.style.display = "block";
			
			password1Input.style.border = "1px solid red";
			password2Input.style.border = "1px solid red";
		}
	}else{
		button.style.backgroundColor = '#115AA3';
		button.disabled = false;
		button.style.cursor = 'pointer';
		
		const inputs = document.querySelectorAll(".settings input");
		
		for(let i = 0; i < inputs.length; i++){
			inputs[i].style.border = "1px solid black";
		}
		
		const emailError = document.querySelector(".email-error");
		const passwordError = document.querySelector(".password-error");
		
		emailError.style.display = "none";
		passwordError.style.display = "none";
	}
}


function validateOpinionForm(e){
	const input = e.target;
	const button = e.target.nextElementSibling;
	const theLength = e.target.value.length;
	const errorMessage = e.target.previousElementSibling;
	
	if(theLength > 400){
		input.style.border = '1px solid red';
		button.style.backgroundColor = '#666';
		button.disabled = true;
		button.style.cursor = 'default';
		errorMessage.style.display = 'block';
	}else{
		input.style.border = '1px solid #115AA3';
		button.style.backgroundColor = '#115AA3';
		button.disabled = false;
		button.style.cursor = 'pointer';
		errorMessage.style.display = 'none';
	}
}


function displayUserCart(){
    purchasedGoods.style.display = 'none';
    settings.style.display = 'none';
    userCart.style.display = 'block';

    showPurchasedGoods.classList.remove("active");
    showSettings.classList.remove("active");
    showUserCart.classList.remove("active");

    showPurchasedGoods.className = 'inactive';
    showSettings.className = 'inactive';
    showUserCart.className = 'active';
}


function displayPurchasedGoods(){
    userCart.style.display = 'none';
    settings.style.display = 'none';
    purchasedGoods.style.display = 'block';

    showPurchasedGoods.classList.remove("active");
    showSettings.classList.remove("active");
    showUserCart.classList.remove("active");

    showSettings.className = 'inactive';
    showUserCart.className = 'inactive';
    showPurchasedGoods.className = 'active';
}

function displaySettings(){
    userCart.style.display = 'none';
    purchasedGoods.style.display = 'none';
    settings.style.display = 'block';

    showPurchasedGoods.classList.remove("active");
    showSettings.classList.remove("active");
    showUserCart.classList.remove("active");

    showUserCart.className = 'inactive';
    showPurchasedGoods.className = 'inactive';
    showSettings.className = 'active';
}