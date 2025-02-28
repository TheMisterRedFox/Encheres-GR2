package fr.eni.encheres.dal.vente;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ICrudRepository;

import java.util.List;
import java.util.Optional;

public interface VenteRepository extends ICrudRepository<ArticleVendu> {
    List<ArticleVendu> findByCategory(int noCategory);


    void encherir(ArticleVendu article, int Montant); // TODO USERS 

    List<ArticleVendu> findBySearchText(String searchWordFilter);

    List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory);

}
