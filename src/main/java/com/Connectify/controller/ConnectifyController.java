package com.Connectify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnectifyController {

	
	@GetMapping("/")
	public String welcome(Model model) {
		return "redirect:/index.html";
	}
}
