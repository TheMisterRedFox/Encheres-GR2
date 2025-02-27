package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/encheres")
public class EnchereController {
	
	@GetMapping
	private String afficherEncheres() {
		return "index";
	}
}
	