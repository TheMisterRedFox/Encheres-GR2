package fr.eni.encheres.dal.article;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.retrait.RetraitRepository;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	private RetraitRepository retraitRepo;
		
	public ArticleRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, 
			JdbcTemplate jdbcTemplate, RetraitRepository retraitRepo) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
		this.retraitRepo = retraitRepo;
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
		KeyHolder keyHolder = new GeneratedKeyHolder();
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
		
		namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"no_article"});
		newArticle.setNoArticle(keyHolder.getKeyAs(Integer.class));
		newArticle.getRetrait().setArticle(newArticle);
		retraitRepo.add(newArticle.getRetrait());
	}
	
	@Override
	public void update(ArticleVendu entity) {
		
	}

	@Override
	public void delete(int id) {

	}
	
	@Override
	public void encherir(int noArticle, Enchere enchere, int Montant) {
		String sql = "update encheres set montant_enchere = :montantEnchere where no_article = :article)";
		String sqlCredit = "update utilisateurs set credit = :creditDebit wherer no_utilisateur = :utilisateur";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params
			  .addValue("montantEnchere", Montant)
			  .addValue("article", noArticle);
		MapSqlParameterSource paramsCredit = new MapSqlParameterSource();
		paramsCredit
			  .addValue("creditDebit", user.getCredit()-Montant)
			  .addValue("utilisateur", user.getNoUtilisateur());
		
		
		
		if ( (Montant - user.getCredit() >=0) || Montant > article.getMeilleureOffre()) 
		{
			namedParameterJdbcTemplate.update(sql, params);
			namedParameterJdbcTemplate.update(sqlCredit, paramsCredit);
		}

	}
}
