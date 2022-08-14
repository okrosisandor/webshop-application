package com.myproject.webshop.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myproject.webshop.service.CustomerService;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

	@Autowired
	CustomerService cService;

	@Override
	public boolean isValid(String inputEmail, ConstraintValidatorContext context) {
		
		List<String> users = new ArrayList<>();
		
		if(cService != null) {
			users = cService.getEmails();
		}
		
		if(inputEmail == null || users.size() == 0) return true;
		
		if(inputEmail.length() < 5) return true;
		
		boolean isUnique = true;
		
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).equalsIgnoreCase(inputEmail)) {
				isUnique = false;
				break;
			}
		}
		
		return isUnique;
		
//		return true;
	}
	
}
