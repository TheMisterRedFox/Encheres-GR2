package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	private final UtilisateurService utilisateurService;
	PasswordEncoder passwordEncoder;

	public UtilisateurController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
		this.utilisateurService = utilisateurService;
		this.passwordEncoder = passwordEncoder;
	}

	// Affiche la liste des utilisateurs
	@GetMapping(path={"/", ""})
	public String afficherUtilisateurs(Model model){

		List<Utilisateur> utilisateurs = utilisateurService.findAll();

		model.addAttribute("utilisateurs", utilisateurs);
		model.addAttribute("body", "pages/utilisateurs/liste-utilisateurs");
		return "index";
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

	// Gère la modification d'un utilisateur
	@GetMapping("/modifier/{noUtilisateur}")
	public String modifierUtilisateur(@PathVariable("noUtilisateur") int noUtilisateur, Model model) {
		Optional<Utilisateur> utilisateur = utilisateurService.findById(noUtilisateur);

		if (utilisateur.isEmpty()){
			return "redirect:/utilisateurs/";
		}

		if(!model.containsAttribute("utilisateur")) {
			model.addAttribute("utilisateur", utilisateur.get());
		}

		model.addAttribute("formRedirectPath", "/utilisateurs/enregistrer");
		model.addAttribute("body", "pages/utilisateurs/formulaire-utilisateur");
		return "index";
	}

	// Gère la modification d'utilisateurs
	@PostMapping("/enregistrer")
	public String enregistrerUtilisateur(
			@Valid @ModelAttribute("utilisateurs") Utilisateur utilisateur,
			BindingResult resultat,
			HttpSession session,
			RedirectAttributes redirectAttr){

		if (resultat.hasErrors()) {
			redirectAttr.addFlashAttribute("utilisateur", utilisateur);
			redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.utilisateur", resultat);
			return "redirect:/utilisateurs/modifier/"+utilisateur.getNoUtilisateur();
		}

		try {
			utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
			utilisateurService.save(utilisateur);
		} catch (Exception ex) {
			redirectAttr.addFlashAttribute("erreur", "Une erreur est survenue lors de l'enregistrement.");
			return "redirect:/utilisateurs/modifier";
		}

		return "redirect:/ventes";
	}

}
