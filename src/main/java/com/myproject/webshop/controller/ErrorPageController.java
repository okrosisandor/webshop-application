package com.myproject.webshop.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController{
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        
	        String errorMsg = "Something went wrong on our end.";
	        String displayCode = statusCode.toString();

	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "exception/404";
	        }

	        if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            errorMsg = "Internal Server Error.";
	        } else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
	            errorMsg = "This request method is not supported.";
	        } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	            errorMsg = "You don't have permission to access this resource.";
	        }

	        model.addAttribute("errorCode", displayCode);
	        model.addAttribute("errorMessage", errorMsg);
	        return "exception/error";
	    }
	    return "redirect:/";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
}
