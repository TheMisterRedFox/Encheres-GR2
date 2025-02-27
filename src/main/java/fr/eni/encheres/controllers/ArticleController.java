package fr.eni.encheres.controllers;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.categorie.CategorieService;
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

	// Affiche le formulaire de création d'un article
	@GetMapping(path={"/", ""})
	private String afficherArticles(@RequestParam(value = "category", required = false) Integer noCategorie,
									@RequestParam(value = "search", required = false) String search,
									Model model){

		List<ArticleVendu> articles = new ArrayList<>();

		if (noCategorie != null || search != null) {
			articles = articleService.findByFilter(search, noCategorie);
		} else {
			articles = articleService.findAll();
		}

		model.addAttribute("articles", articles);
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/articles/liste-articles");
		return "index";
	}

	@GetMapping("/ajouter")
	private String afficherFormArticle(Model model) {
		model.addAttribute("article", new ArticleVendu());
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/articles/formulaire-articles");
		return "index";
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

	@PostMapping("/filtrer")
	private String filtrerVentes(@RequestParam("category") int noCategorie,
								 @RequestParam ("search") String search,
								 RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("category", noCategorie);
		redirectAttributes.addAttribute("search", search);
		return "redirect:/articles/";
	}

    @PostMapping("/enchere")
    public String Encherir(@ModelAttribute ArticleVendu article,@RequestParam("montantEnchere") int MontantEnchere){	
    		Optional<ArticleVendu> optArticle = articleService.findById(article.getNoArticle());
    		if (optArticle.isPresent()) {
    			
    			articleService.encherir(optArticle.get(),MontantEnchere);
    		}
          
        return "redirect:/articles/";
    }
}
