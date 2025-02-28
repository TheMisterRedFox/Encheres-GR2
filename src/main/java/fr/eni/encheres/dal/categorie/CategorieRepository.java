package fr.eni.encheres.dal.categorie;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ICrudRepository;

import java.util.Optional;

public interface CategorieRepository extends ICrudRepository<Categorie> {
    Optional<Categorie> findByLibelle(String libelle);
}
