package fr.eni.encheres.controllers;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.categorie.CategorieService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/articles")
public class ArticleController {

	private final ArticleService articleService;
	private final CategorieService categorieService;
	
	public ArticleController(ArticleService articleService, CategorieService categorieService) {
		this.articleService = articleService;
		this.categorieService = categorieService;
	}

	@GetMapping(path={"/", ""})
	private String afficherArticles(@RequestParam(value = "category", required = false) Integer noCategorie,
									@RequestParam(value = "search", required = false) String search,
									Model model){

		List<ArticleVendu> articles = new ArrayList<>();

		if (noCategorie != null || search != null) {

		} else {
			articles = articleService.findAll();
		}

		model.addAttribute("articles", articles);
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/articles/liste-articles.html");
		return "index";
	}

	@GetMapping("/ajouter")
	private String afficherFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/articles/formulaire-articles");
		return "index";
	}
	
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

	@PostMapping("/filtrer")
	private String filtrerVentes(@RequestParam("category") int noCategorie,
								 @RequestParam ("search") String search,
								 RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("category", noCategorie);
		redirectAttributes.addAttribute("search", search);
		return "redirect:/";
	}
}
