package com.myproject.webshop.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myproject.webshop.service.CustomerService;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{
	
	@Autowired
	CustomerService cService;

	@Override
	public boolean isValid(String inputUsername, ConstraintValidatorContext context) {
		
		List<String> users = new ArrayList<>();
		
		if(cService != null) {
			users = cService.getUsernames();
		}
		
		if(inputUsername == null || users.size() == 0) return true;
		
		if(inputUsername.length() < 5) return true;
		
		boolean isUnique = true;
		
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).equalsIgnoreCase(inputUsername)) {
				isUnique = false;
				break;
			}
		}
		
		return isUnique;
		
//		return true;
		
	}

}
