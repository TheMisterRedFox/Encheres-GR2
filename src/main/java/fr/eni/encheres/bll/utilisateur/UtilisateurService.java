package fr.eni.encheres.bll.utilisateur;

import fr.eni.encheres.bll.ICrudService;
import fr.eni.encheres.bo.Utilisateur;

import java.util.Optional;

public interface UtilisateurService extends ICrudService<Utilisateur>{
    Optional<Utilisateur> findByPseudo(String pseudo);

}
