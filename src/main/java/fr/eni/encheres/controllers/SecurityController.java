package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UsernameAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {

    UtilisateurService utilisateurService;
    PasswordEncoder passwordEncoder;

    public SecurityController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "pages/security/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if(!model.containsAttribute("utilisateur")) {
            model.addAttribute("utilisateur", new Utilisateur());
        }
        // Variables du model lié à la page register
        model.addAttribute("formRedirectPath", "/register");
        model.addAttribute("h1Title", "S'inscrire");
        model.addAttribute("submitButtonLabel", "S'inscrire");
        return "pages/security/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult resultat, RedirectAttributes redirectAttr) {

        if(resultat.hasErrors()) {
            redirectAttr.addFlashAttribute("utilisateur", utilisateur);
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.utilisateur", resultat);
            return "redirect:/register";
        }

        try {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            utilisateurService.save(utilisateur);
        } catch (UsernameAlreadyExistsException e) {
            redirectAttr.addFlashAttribute("utilisateur", utilisateur);
            redirectAttr.addFlashAttribute("erreur", "Le pseudo est déjà utilisé. Merci d'en choisir un autre.");
            return "redirect:/register";
        }

        return "redirect:/";
    }
}

