package com.Connectify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;
import com.Connectify.service.UserService;



@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(UserService theUserService) {
		userService = theUserService;
	}
	
	
	
	
	@GetMapping("/show_form")
    public String showForm(Model model) {
        UserData userData = new UserData();
        model.addAttribute("user_data", userData);
         
        return "users/register_form";
    }
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user_data")UserData userData,Model theModel) {
		
	
		if(userData.getName().isEmpty()) {
			theModel.addAttribute("errorMessage", "Blank name");
			return "users/register_form";
		}
		
		else if(userData.getEmail().isEmpty()) {
			theModel.addAttribute("errorMessage", "Blank email");
			return "users/register_form";
		}
		
		else if(userData.getPassword().isEmpty()) {
			theModel.addAttribute("errorMessage", "Blank password");
			return "users/register_form";
		}
		else if (!isValidEmail(userData.getEmail())) {
			theModel.addAttribute("errorMessage", "Wrong email format");
			return "users/register_form";
		}
		User user = userService.findByName(userData.getName());
		if (user != null ) {
			theModel.addAttribute("errorMessage", "User exists");
			return "users/register_form";
		}
		
		userService.register(userData);
		return "redirect:/index.html";
	}
	
	private static boolean isValidEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

}