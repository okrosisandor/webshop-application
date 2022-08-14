package com.myproject.webshop.aspect;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.myproject.webshop.entity.Visitor;
import com.myproject.webshop.service.CustomerService;

@Aspect
@Component
public class Visitors {
	
	@Autowired
	CustomerService customerService;

	@Pointcut("execution(* com.myproject.webshop.controller.*.*(..))")
	public void controller() {}
	
	@After("controller()")
	private void countVisitors() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String ip = request.getRemoteAddr();
		
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		
		List<Visitor> todaysVisitors = customerService.todaysVisitors();
		
		for(Visitor visitor : todaysVisitors) {
			if(visitor.getIpAddress().equals(ip)) {
				return;
			}
		}
		
		customerService.saveVisit(new Visitor(ip, new Date()));
	}
}
