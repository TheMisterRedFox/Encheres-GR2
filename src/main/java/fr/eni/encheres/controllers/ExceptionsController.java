package fr.eni.encheres.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionsController {

    // Gestion des erreurs générales
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("body", "pages/errors/error");
        model.addAttribute("message", "Une erreur inattendue est survenue.");
        model.addAttribute("details", ex.getMessage());
        return "index";
    }

    // Gestion des erreurs 404 (page non trouvée)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("body", "pages/errors/error");
        model.addAttribute("message", "Page non trouvée.");
        model.addAttribute("details", "L'URL demandée est introuvable.");
        return "index";
    }

    // Gestion des erreurs de requêtes SQL mal formées
    @ExceptionHandler(BadSqlGrammarException.class)
    public String handleBadSqlGrammar( Model model) {
        model.addAttribute("body", "pages/errors/error");
        model.addAttribute("message", "Erreur SQL : requête mal formée.");
        model.addAttribute("details", "Veuillez contacter un administrateur.");
        return "index";
    }

    // Gestion des erreurs d'intégrité (ex : violation de clé étrangère)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(Model model) {
        model.addAttribute("body", "pages/errors/error");
        model.addAttribute("message", "Erreur d'intégrité des données.");
        model.addAttribute("details", "L'opération ne peut pas être effectuée car elle viole une contrainte de la base de données.");
        return "index";
    }
}
