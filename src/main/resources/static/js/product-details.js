const bigImage = document.querySelector('#big-image');
const bigImageModal = document.querySelector('.bigger-image-modal');
const closeModalButton = document.querySelector('.bigger-image-modal #close');

if(bigImage != null){
	bigImage.addEventListener('click', showImageInBiggerSize);
}

closeModalButton.addEventListener('click', closeModal);
window.addEventListener('click', clickOutside);
document.addEventListener('DOMContentLoaded', getRatings);


function showImageInBiggerSize(){
    document.body.style.overflow = 'hidden';
    bigImageModal.style.display = 'block';
}

function closeModal(){
    bigImageModal.style.display = 'none';
    document.body.style.overflow = 'auto';
}

function clickOutside(e){
    if(e.target == bigImageModal){
        bigImageModal.style.display = 'none';
        document.body.style.overflow = 'auto';
    }
}

function getRatings(){
	
	//average stars
	
	let rating = document.querySelector(".rating").innerHTML;
	let ratingValue = rating.replace(",", ".");
	
	const starPercentage = (ratingValue / 5) * 100;
	
	const starPercentageRounded = `${Math.round(starPercentage / 10) * 10}%`;
	
	document.querySelector(`.review .stars-inner`).style.width = starPercentageRounded;
	
	
	//opinion stars
	
	let addedrating = document.querySelectorAll(".customer-rating");
	
	let formatted = [];
	for(let i = 0; i < addedrating.length; i++){
		let temp = addedrating[i].innerHTML;
		
		let opinionStarPercentage = (temp / 5) * 100;
		
		const opinionStarPercentageRounded = `${Math.round(opinionStarPercentage / 10) * 10}%`;
		formatted.push(opinionStarPercentageRounded);
	}
	
	for(let i = 0; i < addedrating.length; i++){
		const current = addedrating[i].previousElementSibling.lastElementChild.firstElementChild;
		current.style.width = formatted[i];
	}
	
	
}