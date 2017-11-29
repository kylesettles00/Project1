package com.revature.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.beans.User;
import com.revature.services.UserService;

public class UserController {
	
	private UserService us = new UserService();
	
	public void processGet(HttpServletRequest request, HttpServletResponse response) {
String actualURL = request.getRequestURI().substring(request.getContextPath().length());
		
		switch(actualURL) {
		case "/users/logout":
			logout(request,response);
			break;
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		
		request.getSession().setAttribute("user", null);
	}

	public void processPost(HttpServletRequest request, HttpServletResponse response) {

		String actualURL = request.getRequestURI().substring(request.getContextPath().length());
		
		switch(actualURL) {
		case "/users/login":
			login(request,response);
			break;
		case "/users/register":
			register(request,response);
			break;
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		String json;
		try {
			json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();
			System.out.println("request to register new user");
			
			ObjectMapper om = new ObjectMapper();
			
			User u = om.readValue(json, User.class);
						
			int newUser = us.register(u);
			
			u.setId(newUser);
			
			if(newUser == 0) {
				response.setStatus(401);
			}
			else {
				request.getSession().setAttribute("user", u);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		String json;
		try {
			json = request.getReader().lines().reduce((acc, curr) -> acc +curr).get();
		
			System.out.println("request to login recieved");
			ObjectMapper om = new ObjectMapper();
		
			User u = om.readValue(json, User.class);
			
			User actualUser = us.login(u);
			
			ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
			String jsonUser = ow.writeValueAsString(actualUser);
			
			if(actualUser == null) {
				response.setStatus(401);
			}
			else {
				request.getSession().setAttribute("user", actualUser.getId());
				response.setHeader("user", jsonUser);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
