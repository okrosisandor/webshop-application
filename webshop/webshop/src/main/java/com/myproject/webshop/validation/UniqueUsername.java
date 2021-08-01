package com.myproject.webshop.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueUsername {

	public String message() default "Username already taken";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};
}
