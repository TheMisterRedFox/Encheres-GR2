package fr.eni.encheres.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeControlleur {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String redirectToVentes() {
        return "redirect:/ventes";
    }

    @GetMapping("/chiffrer")
    public String chiffrer(@RequestParam("mot") String mot) {
        System.out.println("[" + passwordEncoder.encode(mot) + "]");
        return "redirect:/";
    }
}
