const ordersDetails = document.querySelectorAll('.processed .details');
const customerDetailsModal = document.querySelectorAll('.customer-details-modal');
const closeOrdersModalButton = document.querySelectorAll('.customer-details-modal #close');

const previousReviewDetails = document.querySelectorAll('.previous-orders .processed .reviews');
const previousOrdersCustomerReviewsModal = document.querySelectorAll('.previous-orders .former-customer-review-modal');
const closePreviousReviewModalButton = document.querySelectorAll('.previous-orders .former-customer-review-modal #close');

const showStandingOrders = document.querySelector('#show-standing-orders');
const showPreviousOrders = document.querySelector('#show-previous-orders');
const showStatistics = document.querySelector('#show-statistics');
const standingOrders = document.querySelector('.standing-orders');
const previousOrders = document.querySelector('.previous-orders');
const statistics = document.querySelector('.statistics');

showStandingOrders.addEventListener('click', displayStandingOrders);
showPreviousOrders.addEventListener('click', displayPreviousOrders);
showStatistics.addEventListener('click', displayStatistics);


window.addEventListener('click', clickOutside);

for (let i = 0; i < ordersDetails.length; i++) {
    ordersDetails[i].addEventListener('click', showCustomerDetails);
}

for (let i = 0; i < closeOrdersModalButton.length; i++) {
    closeOrdersModalButton[i].addEventListener('click', closeModal);
}


window.addEventListener('click', clickOutsideForPreviousReview);


for (let i = 0; i < previousReviewDetails.length; i++) {
    previousReviewDetails[i].addEventListener('click', showReviewModal);
}

for (let i = 0; i < closePreviousReviewModalButton.length; i++) {
    closePreviousReviewModalButton[i].addEventListener('click', closeModal);
}


function showCustomerDetails(e){
    document.body.style.overflow = 'hidden';
    
    e.target.parentElement.nextElementSibling.style.display = "block";
}

function closeModal(e){
	e.target.parentElement.parentElement.parentElement.parentElement.style.display = 'none';
	
    document.body.style.overflow = 'auto';
}

function clickOutside(e){
	
	for(let i = 0; i < customerDetailsModal.length; i++){
		if(e.target == customerDetailsModal[i]){
	        customerDetailsModal[i].style.display = 'none';
	        document.body.style.overflow = 'auto';
		}
	}
}


function showReviewModal(e){
    document.body.style.overflow = 'hidden';
    
    e.target.parentElement.nextElementSibling.nextElementSibling.style.display = "block";
    
    const voted = e.target.parentElement.nextElementSibling.nextElementSibling.firstElementChild.firstElementChild.nextElementSibling.firstElementChild;
    const theClass = voted.className;
    
    if(theClass.localeCompare("not-reviewed") == 0){
		voted.style.display = "block";
	}
    
}

function clickOutsideForPreviousReview(e){
    for(let i = 0; i < previousOrdersCustomerReviewsModal.length; i++){
		if(e.target == previousOrdersCustomerReviewsModal[i]){
	        previousOrdersCustomerReviewsModal[i].style.display = 'none';
	        document.body.style.overflow = 'auto';
		}
	}
}


function displayStandingOrders(){
    previousOrders.style.display = 'none';
    statistics.style.display = 'none';
    standingOrders.style.display = 'block';

    showPreviousOrders.classList.remove("active");
    showStatistics.classList.remove("active");
    showStandingOrders.classList.remove("active");

    showPreviousOrders.className = 'inactive';
    showStatistics.className = 'inactive';
    showStandingOrders.className = 'active';
}


function displayPreviousOrders(){
    standingOrders.style.display = 'none';
    statistics.style.display = 'none';
    previousOrders.style.display = 'block';

    showPreviousOrders.classList.remove("active");
    showStatistics.classList.remove("active");
    showStandingOrders.classList.remove("active");

    showStatistics.className = 'inactive';
    showStandingOrders.className = 'inactive';
    showPreviousOrders.className = 'active';
}

function displayStatistics(){
    standingOrders.style.display = 'none';
    previousOrders.style.display = 'none';
    statistics.style.display = 'block';

    showPreviousOrders.classList.remove("active");
    showStatistics.classList.remove("active");
    showStandingOrders.classList.remove("active");

    showStandingOrders.className = 'inactive';
    showPreviousOrders.className = 'inactive';
    showStatistics.className = 'active';
}


