package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.eni.encheres.bll.utilisateur.UtilisateurService;

import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	private final UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	// Affiche le profil d'un utilisateur
	@GetMapping("/{noUtilisateur}")
	public String profil(@PathVariable("noUtilisateur") int noUtilisateur, Model model, HttpSession session) {
		Optional<Utilisateur> optUtilisateur = utilisateurService.findById(noUtilisateur);

		if(optUtilisateur.isPresent()) {
			model.addAttribute("user", optUtilisateur.get());
			model.addAttribute("body", "pages/utilisateurs/profil-utilisateur");
			return "index";
		}
		return "redirect:/utilisateurs/";
	}
}
