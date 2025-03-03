package fr.eni.encheres.bll.vente;

import fr.eni.encheres.bll.ICrudService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface VenteService extends ICrudService<ArticleVendu> {
    List<ArticleVendu> findByFilter(String searchWordFilter, int noCategory);

    List<ArticleVendu> findByCategory(int noCategory);

    List<ArticleVendu> findBySearchText(String searchWordFilter);

    List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory);

    void encherir(ArticleVendu article,  int Montant/*,Utilisateur utilisateur*/); // TODO USER 
    
    String finEnchere(ArticleVendu article, Utilisateur user);

}
