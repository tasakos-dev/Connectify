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

/**
 * Controller class for handling user register requests.
 * @author tasakos
 *
 */
@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(UserService theUserService) {
		userService = theUserService;
	}
	
	/**
	 * Displays the user registration form.
	 *
	 * @param model the model to be populated with data for the view
	 * @return the view name to display ("users/register_form")
	 */
	@GetMapping("/show_form")
    public String showForm(Model model) {
        UserData userData = new UserData();
        model.addAttribute("user_data", userData);
         
        return "users/register_form";
    }
	
	/**
	 * Registers a new user based on the submitted form data.
	 *
	 * @param userData the user data submitted from the registration form
	 * @param theModel the model to be populated with data for the view
	 * @return the view name to redirect to ("redirect:/index.html") upon successful registration,
	 *         or the view name to display ("users/register_form") with an error message if registration fails
	 */
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user_data")UserData userData,Model theModel) {
		if(userData.getEmail().isEmpty()) {
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
		User user = userService.findByEmail(userData.getEmail());
		if (user != null ) {
			theModel.addAttribute("errorMessage", "User exists");
			return "users/register_form";
		}
		
		userService.register(userData);
		return "redirect:/index.html";
	}
	
	/**
	 * Validates an email address using a regular expression pattern.
	 *
	 * @param email the email address to validate
	 * @return true if the email address is valid, false otherwise
	 */
	private static boolean isValidEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
}