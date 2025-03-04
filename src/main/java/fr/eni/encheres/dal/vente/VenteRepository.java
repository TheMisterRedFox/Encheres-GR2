package fr.eni.encheres.dal.vente;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ICrudRepository;

import java.util.List;

public interface VenteRepository extends ICrudRepository<ArticleVendu> {
    List<ArticleVendu> findByCategory(int noCategory);


    void encherir(ArticleVendu article, Utilisateur user, int Montant); 
    
    String finEnchere(ArticleVendu article, Utilisateur user); 

    List<ArticleVendu> findBySearchText(String searchWordFilter);

    List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory);
    
    void archiver(ArticleVendu article); 

}
