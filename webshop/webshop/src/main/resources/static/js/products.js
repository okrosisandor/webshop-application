document.addEventListener('DOMContentLoaded', getRatings);

function getRatings(){

	let averageRating = document.querySelectorAll(".average-rating");
	let averageRatingVal = [];
	
	for(let i = 0; i < averageRating.length; i++){
		averageRatingVal.push(averageRating[i].innerHTML);
	}
	
	
	let formatted = [];
	for(let i = 0; i < averageRatingVal.length; i++){
		let temp = averageRating[i].innerHTML;
		
		let opinionStarPercentage = (temp / 5) * 100;
		
		const opinionStarPercentageRounded = `${Math.round(opinionStarPercentage / 10) * 10}%`;
		formatted.push(opinionStarPercentageRounded);
	}
	
	for(let i = 0; i < averageRating.length; i++){
		const current = averageRating[i].previousElementSibling.firstElementChild.firstElementChild;
		current.style.width = formatted[i];
	}
	
}