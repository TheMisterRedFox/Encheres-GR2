package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.categorie.CategorieService;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.CategoryAlreadyExistsException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategorieController {
    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @ModelAttribute("categorie")
    public Categorie createCategorie() {
        return new Categorie();
    }

    @GetMapping(path={"/", ""})
    private String afficherCategories(Model model){

        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("body", "pages/categories/liste-categories.html");
        return "index";
    }

    @GetMapping("/ajouter")
    private String ajouterCategorie(Model model) {
        if(!model.containsAttribute("categorie")){
            model.addAttribute("categorie", new Categorie());
        }
        model.addAttribute("body", "pages/categories/formulaire-categorie.html");
        return "index";
    }

    @GetMapping("/{noCategorie}/modifier")
    public String modifierCategorie(@PathVariable(name="noCategorie") int noCategorie, Model modele) {
        Optional<Categorie> categorieOpt = categorieService.findById(noCategorie);

        if (categorieOpt.isPresent()) {
            modele.addAttribute("categorie", categorieOpt.get());
            modele.addAttribute("title", "Modifier la catégorie");
            modele.addAttribute("body", "pages/categories/formulaire-categorie.html");
        } else {
            return "redirect:/categories";
        }
        return "index";
    }

    @PostMapping("/enregistrer")
    private String enregistrerCategorie(@Valid @ModelAttribute Categorie categorie, BindingResult resultat, Model model, RedirectAttributes redirectAttr) {

        if(resultat.hasErrors()) {
            redirectAttr.addFlashAttribute("categorie", categorie);
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.categorie", resultat);
            return "redirect:/categories/ajouter";
        }
        try {
            categorieService.save(categorie);
        } catch (CategoryAlreadyExistsException ex) {
            model.addAttribute("categorie", categorie);
            redirectAttr.addFlashAttribute("erreur", "Une catégorie avec ce libellé existe déjà.");
            return "redirect:/categories/ajouter";
        }

        return "redirect:/categories";
    }

    @GetMapping(path="/{noCategorie}")
    private String afficherDetailCategorie(@PathVariable("noCategorie") int noCategorie, Model model){
        Optional<Categorie> categorie = categorieService.findById(noCategorie);
        if(categorie.isPresent()){
            model.addAttribute("categorie", categorie.get());
            model.addAttribute("body", "pages/categories/details-categorie.html");
            return "index";
        }

        return "redirect:/categories/";
    }

    @GetMapping("/{noCategorie}/supprimer")
    private String supprimerCategorie(@PathVariable("noCategorie") int noCategorie, RedirectAttributes redirectAttr){
        try{
            categorieService.delete(noCategorie);
        } catch (CategoryAlreadyExistsException ex) {
            redirectAttr.addFlashAttribute("erreur", "Impossible de supprimer cette catégorie, elle est associée à des ventes.");
            return "redirect:/categories/" + noCategorie;
        }

        return "redirect:/categories/";
    }


}
