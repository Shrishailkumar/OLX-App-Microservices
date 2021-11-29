package com.olx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.olx.dto.User;

@Controller
public class UserController {
	static List<User> users = new ArrayList<User>();
	static{
		users.add(new User("shrishail","Bangalore"));
		users.add(new User("Neha","Bijapur"));
		users.add(new User("Raghu","Belgaum"));
	}
	
	@GetMapping(value="/welcome-user")
	public String welcome(Model model) {
		model.addAttribute("name","shrishailkumar");
		model.addAttribute("users",users);
		return "Welcome";
		
	}

}
