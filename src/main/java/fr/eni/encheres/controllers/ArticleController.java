package fr.eni.encheres.controllers;

import org.springframework.ui.Model;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.categorie.CategorieService;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	private final ArticleService articleService;
	private final CategorieService categorieService;

	// Controlleur
	public ArticleController(ArticleService articleService, CategorieService categorieService) {
		this.articleService = articleService;
		this.categorieService = categorieService;
	}

	// Affiche le formulaire de création d'un article
	@GetMapping("/ajouter")
	private String afficherFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		model.addAttribute("categories", categorieService.findAll());
		return "pages/articles/formulaire-articles";
	}

	// Affiche les details d'une vente
	@GetMapping("/{noArticle}")
	public String getVenteDetails(@PathVariable("noArticle") int noArticle, Model model) {

		Optional<ArticleVendu> optArticle = articleService.findById(noArticle);

		if(optArticle.isPresent()) {
			model.addAttribute("article", optArticle.get());
			return "pages/ventes/details-vente";
		}
		return "redirect:/articles/";
	}

	// Gère la redirection après un enregistrement
	@PostMapping("/enregistrer")
	private String ajouterArticle(@ModelAttribute ArticleVendu article, 
			@RequestParam("category") int noCategorie,
			@RequestParam ("rue") String rue,
			@RequestParam ("codePostal") String codePostal,
			@RequestParam ("ville") String ville) {
		Optional<Categorie> optCategorie = categorieService.findById(noCategorie);

		if(optCategorie.isPresent()) {
			article.setCategorie(optCategorie.get());
		}

		article.setRetrait(new Retrait(rue, codePostal, ville));
		articleService.save(article);
		return "redirect:/articles";
	}
}
