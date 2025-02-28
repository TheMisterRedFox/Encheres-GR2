package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControlleur {

    @GetMapping("/")
    public String redirectToVentes() {
        return "redirect:/ventes";
    }
}
