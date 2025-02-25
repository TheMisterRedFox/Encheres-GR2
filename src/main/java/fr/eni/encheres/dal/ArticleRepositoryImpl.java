package fr.eni.encheres.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
		
	public ArticleRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ArticleVendu> findAll() {
		return null;
	}

	@Override
	public Optional<ArticleVendu> findById(int id) {
		return Optional.empty();
	}

	@Override
	public void add(ArticleVendu newArticle) {
		String sql = "insert into articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial,"
				+ "no_utilisateur, no_categorie) values (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :miseAPrix,"
				+ ":noUtilisateur, :noCategorie)"; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nomArticle", newArticle.getNomArticle())
		      .addValue("description", newArticle.getDescription())
		      .addValue("dateDebutEncheres", newArticle.getDateDebutEncheres())
		      .addValue("dateFinEncheres", newArticle.getDateFinEncheres())
		      .addValue("miseAPrix", newArticle.getMiseAPrix())
		      .addValue("noUtilisateur", 1) //TODO CHANGER LES 1 PAR LES ID
		      .addValue("noCategorie", newArticle.getCategorie().getNoCategorie()); 
		
		namedParameterJdbcTemplate.update(sql, params);
	}
	
	@Override
	public void update(ArticleVendu entity) {
		
	}

	@Override
	public void delete(int id) {

	}

}
