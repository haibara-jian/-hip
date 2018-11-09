package com.biboheart.huip.patient.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage() {
		return "index";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}
	
	@RequestMapping(value = "/demo")
	@ResponseBody
	public String username(Principal principal, HttpSession session) {
		System.out.println("1:" + principal.getName());
		Object springSecurityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (springSecurityContext instanceof SecurityContext) {
			SecurityContext sc = (SecurityContext) springSecurityContext;
			Authentication authentication = sc.getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				System.out.println("2:" + authentication.getName());
			}
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				System.out.println("3:" + authentication.getName());
			}
		}
		return principal.getName();
	}

}
