package fr.eni.encheres.bll.article;

import fr.eni.encheres.bll.ICrudService;
import fr.eni.encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleService extends ICrudService<ArticleVendu> {
    List<ArticleVendu> findByFilter(String searchWordFilter, int noCategory);

    List<ArticleVendu> findByCategory(int noCategory);

    List<ArticleVendu> findBySearchText(String searchWordFilter);

    List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory);
}