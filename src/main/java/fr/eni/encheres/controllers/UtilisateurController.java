package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.eni.encheres.bll.utilisateur.UtilisateurService;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	private final UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
}
