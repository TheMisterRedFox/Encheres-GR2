package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.eni.encheres.bll.vente.VenteService;
import fr.eni.encheres.bll.categorie.CategorieService;
import fr.eni.encheres.bll.utilisateur.UtilisateurService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventes")
public class VenteController {

	private final VenteService venteService;
	private final CategorieService categorieService;
	private final UtilisateurService utilisateurService;


	public VenteController(VenteService venteService, CategorieService categorieService, UtilisateurService utilisateurService) {
		this.venteService = venteService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
	}

	// Affiche la listes des ventes en cours
	@GetMapping(path={"/", ""})
	private String afficherVentes(@RequestParam(value = "category", required = false) Integer noCategorie,
									@RequestParam(value = "search", required = false) String search,
									Model model){

		List<ArticleVendu> articles = new ArrayList<>();

		if (noCategorie != null || search != null) {
			articles = venteService.findByFilter(search, noCategorie);
		} else {
			articles = venteService.findAll();
		}

		model.addAttribute("articles", articles);
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/ventes/liste-ventes");
		return "index";
	}

	// Affiche le formulaire de création d'un article
	@GetMapping("/ajouter")
	private String afficherFormVente(Model model) {
		model.addAttribute("vente", new ArticleVendu());
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/ventes/formulaire-ventes");
		return "index";
	}

	@GetMapping("/modifier/{id}")
	public String afficherFormulaireModification(@PathVariable int id, Model model) {
		Optional<ArticleVendu> article = venteService.findById(id);

		if (article.isEmpty()) {
			return "redirect:/ventes";
		}
		model.addAttribute("vente", article.get());
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("body", "pages/ventes/formulaire-ventes");
		return "index";
	}

	// Affiche les details d'une vente
	@GetMapping("/{noArticle}")
	public String getVenteDetails(@PathVariable("noArticle") int noArticle, Model model,HttpSession session) {

		Optional<ArticleVendu> optArticle = venteService.findById(noArticle);
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

		// Pour pouvoir afficher le template thymeleaf même si on est pas connecté on met un utilisateur vide
		if(utilisateur == null) {
			utilisateur = new Utilisateur();
		}

		if (optArticle.get().getDateFinEncheres().isBefore(LocalDateTime.now())||optArticle.get().getDateFinEncheres().isEqual(LocalDateTime.now())){

			model.addAttribute("vente", optArticle.get());
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("body",venteService.finEnchere(optArticle.get(), utilisateur));
			return "index";
		} else {
			model.addAttribute("vente", optArticle.get());
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("body", "pages/ventes/details-vente");
			return "index";
		}
	}

	// Gère la suppression d'un article
	@GetMapping("/supprimer/{noArticle}")
	public String supprimerVente(@PathVariable("noArticle") int noArticle) {
		Optional<ArticleVendu> optArticle = venteService.findById(noArticle);

		if(optArticle.isPresent()) {
			venteService.delete(noArticle);
		}
		return "redirect:/ventes/";
	}

	// Gère la redirection après un enregistrement
	@PostMapping("/enregistrer")
	private String enregistrerVente(@Valid @ModelAttribute("vente") ArticleVendu article,
			BindingResult resultat,
			@RequestParam("category") int noCategorie,
			@RequestParam("rue") String rue,
			@RequestParam("codePostal") String codePostal,
			@RequestParam("ville") String ville,
			HttpSession session,
			RedirectAttributes redirectAttr,
			Model model) {

		if (resultat.hasErrors()) {
			model.addAttribute("categories", categorieService.findAll());
			model.addAttribute("body", "pages/ventes/formulaire-ventes");
			return "index";
		}

		Optional<Categorie> optCategorie = categorieService.findById(noCategorie);
		if (optCategorie.isPresent()) {
			article.setCategorie(optCategorie.get());
		}

		if (session.getAttribute("user") != null) {
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
			if (utilisateur != null) {
				article.setVendeur(utilisateur);
			}
		}

		article.setRetrait(new Retrait(rue, codePostal, ville));

		try {
			venteService.save(article);
		} catch (Exception ex) {
			redirectAttr.addFlashAttribute("erreur", "Une erreur est survenue lors de l'enregistrement.");
			return "redirect:/ventes/ajouter";
		}

		return "redirect:/ventes";
	}

	// Gère le filtre des ventes
	@PostMapping("/filtrer")
	private String filtrerVentes(@RequestParam("category") int noCategorie,
								 @RequestParam ("search") String search,
								 RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("category", noCategorie);
		redirectAttributes.addAttribute("search", search);
		return "redirect:/ventes/";
	}

    @PostMapping("/encherir")
    public String Encherir(@ModelAttribute ArticleVendu article,@RequestParam("montantEnchere") int MontantEnchere, HttpSession session){
    		Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
    		Optional<ArticleVendu> optArticle = venteService.findById(article.getNoArticle());
			Optional<Utilisateur> optUser = utilisateurService.findById(utilisateur.getNoUtilisateur());
    		if (optArticle.isPresent() && optUser.isPresent()) {

				venteService.encherir(optArticle.get(),optUser.get(),MontantEnchere);
    		}

        return "redirect:/ventes/";
    }

    @PostMapping("/archiver")
    public String Archiver(@ModelAttribute ArticleVendu article,@RequestParam("noArticle") int no_article) {
		Optional<ArticleVendu> optArticle = venteService.findById(article.getNoArticle());
		System.out.println(optArticle.get());
		if (optArticle.isPresent()) {

			venteService.archiver(optArticle.get());
		}
		return "redirect:/ventes/";
    }
	// TODO faire des tests unitaire

}
