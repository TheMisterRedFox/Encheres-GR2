package fr.eni.encheres.controllers;

import org.springframework.ui.Model;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.categorie.CategorieService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	private final ArticleService articleService;
	private final CategorieService categorieService;
	
	public ArticleController(ArticleService articleService, CategorieService categorieService) {
		this.articleService = articleService;
		this.categorieService = categorieService;
	}

	@GetMapping("/ajouter")
	private String afficherFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		model.addAttribute("categories", categorieService.findAll());
		return "pages/articles/formulaire-articles";
	}
	
	@PostMapping("/enregistrer")
	private String ajouterArticle(@ModelAttribute ArticleVendu article, @RequestParam("category") int noCategorie) {
		Optional<Categorie> optCategorie = categorieService.findById(noCategorie);
		
		if(optCategorie.isPresent()) {
			article.setCategorie(optCategorie.get());
		}
		
		articleService.save(article);
		return "redirect:/articles";
	}
}
