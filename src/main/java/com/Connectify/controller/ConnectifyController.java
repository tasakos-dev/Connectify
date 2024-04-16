package com.Connectify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling requests related to Connectify.
 * @author tasakos
 *
 */
@Controller
public class ConnectifyController {

	/**
     * Redirects the root URL ("/") to the index page.
     *
     * @param model the model to be populated with data for the view
     * @return the view name to redirect to ("redirect:/index.html")
     */
	@GetMapping("/")
	public String welcome(Model model) {
		return "redirect:/index.html";
	}
}
