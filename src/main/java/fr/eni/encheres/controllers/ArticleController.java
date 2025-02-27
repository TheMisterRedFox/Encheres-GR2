package fr.eni.encheres.controllers;

import org.springframework.ui.Model;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
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
	
	@PostMapping("/{noArticle}/enchere")
	public String Encherir(
			@PathVariable("noArticle") int noArticle,@RequestParam("MontantEnchere") int MontantEnchere,
			@ModelAttribute Enchere enchere) {
	
		articleService.encherir(noArticle,enchere,MontantEnchere);
		return "redirect:/encheres";
		}
    
/*
 * 
proposition - user.getCredit() >= 0
&&
prix >enchere.getMontantEnchere()
then 
enchere.setMontantEnchere(prix)

if ( (enchere.getMontantEnchere() - user.getCredit() >=0) || proposition > enchere.getMontantEnchere()) { }

*/
}
