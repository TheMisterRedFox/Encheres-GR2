package fr.eni.encheres.dal.article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import fr.eni.encheres.bo.ArticleVendu;
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
		String sql = "SELECT AV.no_article, AV.nom_article, AV.description, AV.date_debut_encheres, AV.date_fin_encheres, AV.prix_initial, AV.prix_vente, "
				   + "	   U.no_utilisateur, U.pseudo, U.nom, U.prenom, U.email, U.telephone, U.rue, U.code_postal, U.ville, U.mot_de_passe, U.credit, U.administrateur, "
				   + "	   C.no_categorie, C.libelle, "
				   + "	   R.rue AS retrait_rue, R.code_postal AS retrait_code_postal, R.ville AS retrait_ville "
				   + "FROM ARTICLES_VENDUS AV "
				   + "INNER JOIN UTILISATEURS U "
				   + "	ON U.no_utilisateur = AV.no_utilisateur "
				   + "INNER JOIN CATEGORIES C "
				   + "	ON C.no_categorie = AV.no_categorie "
				   + "INNER JOIN RETRAITS R "
				   + "	ON R.no_article = AV.no_article";

		List<ArticleVendu> articles = jdbcTemplate.query(sql, new ArticleRowMapper());

		return articles;
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

}

class ArticleRowMapper implements RowMapper<ArticleVendu> {
	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArticleVendu article = new ArticleVendu();

		article.setNoArticle(rs.getInt("no_article"));
		article.setNomArticle(rs.getString("nom_article"));
		article.setDescription(rs.getString("description"));
		article.setDateDebutEncheres(rs.getObject("date_debut_encheres", LocalDateTime.class));
		article.setDateFinEncheres(rs.getObject("date_fin_encheres", LocalDateTime.class));
		article.setMiseAPrix(rs.getInt("prix_initial"));
		article.setPrixVente(rs.getInt("prix_vente"));

		Utilisateur vendeur = new Utilisateur();
		vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));
		vendeur.setPseudo(rs.getString("pseudo"));
		vendeur.setNom(rs.getString("nom"));
		vendeur.setPrenom(rs.getString("prenom"));
		vendeur.setEmail(rs.getString("email"));
		vendeur.setTelephone(rs.getString("telephone"));
		vendeur.setRue(rs.getString("rue"));
		vendeur.setCodePostal(rs.getString("code_postal"));
		vendeur.setVille(rs.getString("ville"));
		vendeur.setMotDePasse(rs.getString("mot_de_passe"));
		vendeur.setCredit(rs.getInt("credit"));
		vendeur.setAdministrateur(rs.getBoolean("administrateur"));
		article.setVendeur(vendeur);

		Categorie categorie = new Categorie();
		categorie.setNoCategorie(rs.getInt("no_categorie"));
		categorie.setLibelle(rs.getString("libelle"));
		article.setCategorie(categorie);

		Retrait adresseDeRetrait = new Retrait();
		adresseDeRetrait.setRue(rs.getString("retrait_rue"));
		adresseDeRetrait.setCodePostal(rs.getString("retrait_code_postal"));
		adresseDeRetrait.setVille(rs.getString("retrait_ville"));
		adresseDeRetrait.setArticle(article);
		article.setRetrait(adresseDeRetrait);

		return article;
	}
}
