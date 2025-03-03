package fr.eni.encheres.dal.vente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fr.eni.encheres.bo.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import fr.eni.encheres.dal.retrait.RetraitRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VenteRepositoryImpl implements VenteRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	private RetraitRepository retraitRepo;

	private final String SQL_SELECT = "SELECT * FROM vue_details_ventes ";
		
	public VenteRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
							   JdbcTemplate jdbcTemplate, RetraitRepository retraitRepo) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
		this.retraitRepo = retraitRepo;
	}

	@Override
	public List<ArticleVendu> findAll() {
		String sql = SQL_SELECT;

		List<ArticleVendu> ventes = jdbcTemplate.query(sql, new VenteRowMapper());

		return ventes;
	}

	@Override
	public Optional<ArticleVendu> findById(int id) {

		String sql = SQL_SELECT + "WHERE no_article = ?";
		ArticleVendu vente = jdbcTemplate.queryForObject(sql, new VenteRowMapper(), id);
		
		return Optional.ofNullable(vente);
	}
	
	@Override
	public List<ArticleVendu> findByCategory(int noCategory) {
		String sql = SQL_SELECT + "WHERE no_categorie = ?";

		List<ArticleVendu> articles = jdbcTemplate.query(sql, new VenteRowMapper(), noCategory);

		return articles;
	}

	@Override
	public List<ArticleVendu> findBySearchText(String searchWordFilter) {
		String sql = SQL_SELECT + "WHERE LOWER(nom_article) = LOWER(?)";

		List<ArticleVendu> articles = jdbcTemplate.query(sql, new VenteRowMapper(), searchWordFilter);

		return articles;
	}

	@Override
	public List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory) {
		String sql = SQL_SELECT + "WHERE LOWER(nom_article) = LOWER(?) AND no_categorie = ?";

		List<ArticleVendu> articles = jdbcTemplate.query(sql, new VenteRowMapper(), searchWordFilter, noCategory);

		return articles;
	}

	@Override
	@Transactional // TODO PROCEDURE STOCKÉ
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
		      .addValue("noUtilisateur", newArticle.getVendeur().getNoUtilisateur())
		      .addValue("noCategorie", newArticle.getCategorie().getNoCategorie());
		
		namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"no_article"});
		newArticle.setNoArticle(keyHolder.getKeyAs(Integer.class));
		newArticle.getRetrait().setArticle(newArticle);
		retraitRepo.add(newArticle.getRetrait());
	}
	
	@Override
	public void update(ArticleVendu articleModifie) {
		String sql = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, " +
				"prix_initial = ?, prix_vente = ?, no_categorie = ? WHERE no_article = ?";

		jdbcTemplate.update(sql, articleModifie.getNomArticle(), articleModifie.getDescription(),
			articleModifie.getDateDebutEncheres(), articleModifie.getDateFinEncheres(), articleModifie.getMiseAPrix(),
			articleModifie.getPrixVente(), articleModifie.getCategorie().getNoCategorie(), articleModifie.getNoArticle()
		);
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM retraits WHERE no_article = ?; DELETE FROM encheres WHERE no_article = ?; DELETE FROM articles_vendus WHERE no_article = ?";
		jdbcTemplate.update(sql, id, id, id);
	}

    @Override
    public void encherir(ArticleVendu article, int Montant) { // TODO USER 
        
    	// Création de la ligne enchere
    	String sql = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) "
        		   + "VALUES (NOW(), :montantEnchere,:article, :utilisateur)"; 
        MapSqlParameterSource params = new MapSqlParameterSource();
        params
                .addValue("montantEnchere", Montant)
                .addValue("article", article.getNoArticle() )
        		.addValue("utilisateur", 2);
        
        // Recrédite l'ancien acheteur
        String sqlReCredit = "update utilisateurs set credit = :meilleureOffre where pseudo = :utilisateur";
        MapSqlParameterSource paramsReCredit = new MapSqlParameterSource();
        paramsReCredit
                .addValue("meilleureOffre", /*AJOUTER CREDIT UTILISATEUR */article.getMeilleureOffre())
                .addValue("utilisateur", (article.getPseudoMeilleurAcheteur()));

        
        // Débite l'acheteur
        String sqlDebit = "update utilisateurs set credit = :creditDebit where no_utilisateur = :utilisateur";
        MapSqlParameterSource paramsDebit = new MapSqlParameterSource();
        paramsDebit
                .addValue("creditDebit", /*AJOUTER CREDIT UTILISATEUR */5000-Montant)
                .addValue("utilisateur", 2);
        
        
        if ( (/*AJOUTER CREDIT UTILISATEUR */5000-Montant) >0 && (Montant > article.getMiseAPrix())&&(Montant > article.getMeilleureOffre()))
        {
            namedParameterJdbcTemplate.update(sql, params);
            namedParameterJdbcTemplate.update(sqlReCredit, paramsReCredit);
            namedParameterJdbcTemplate.update(sqlDebit, paramsDebit);
		}
    }

class VenteRowMapper implements RowMapper<ArticleVendu> {
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
		article.setMeilleureOffre(rs.getInt("meilleure_offre"));
		article.setPseudoMeilleurAcheteur(rs.getString("acheteur_pseudo"));

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
}}
