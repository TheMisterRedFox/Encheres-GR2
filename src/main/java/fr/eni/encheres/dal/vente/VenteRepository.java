package fr.eni.encheres.dal.vente;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ICrudRepository;

import java.util.List;

public interface VenteRepository extends ICrudRepository<ArticleVendu> {
    List<ArticleVendu> findByCategory(int noCategory);

    List<ArticleVendu> findBySearchText(String searchWordFilter);

    List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory);
}
