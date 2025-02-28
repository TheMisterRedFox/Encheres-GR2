package fr.eni.encheres.dal.utilisateur;

import java.util.List;
import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ICrudRepository;

public interface UtilisateurRepository extends ICrudRepository<Utilisateur>{

    Optional<Utilisateur> findByPseudo(String pseudo);
}
