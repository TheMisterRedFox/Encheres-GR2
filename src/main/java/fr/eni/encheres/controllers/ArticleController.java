package fr.eni.encheres.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.encheres.bo.ArticleVendu;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	@GetMapping("/ajouter")
	private String ajouterFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		return "pages/articles/formulaire-articles";
	}
}
