package fr.eni.encheres.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bll.ArticleService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	private final ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/ajouter")
	private String afficherFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		return "pages/articles/formulaire-articles";
	}
	
	@PostMapping("/enregistrer")
	private String ajouterArticle(@Valid ArticleVendu article) {
		articleService.save(article);
		return "redirect:/articles";
	}
}
