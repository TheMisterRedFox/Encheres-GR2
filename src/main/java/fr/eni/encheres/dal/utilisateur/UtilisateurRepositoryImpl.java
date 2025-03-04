package fr.eni.encheres.dal.utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	private final String SQL_SELECT = "SELECT * FROM utilisateurs ";

	public UtilisateurRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Utilisateur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Optional<Utilisateur> findById(int noUtilisateur) {
		String sql = SQL_SELECT + "where no_utilisateur = ?";
		Utilisateur user = jdbcTemplate.queryForObject(sql, new UtilisateurRowMapper(), noUtilisateur);

		return Optional.ofNullable(user);
	}

	@Override
	public void add(Utilisateur utilisateur) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "insert into utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
				+ "values (:pseudo, :nom, :prenom, :email, :telephone, "
				+ ":rue, :code_postal, :ville, :mot_de_passe, :credit, :administrateur)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", utilisateur.getPseudo())
				.addValue("nom", utilisateur.getNom())
				.addValue("prenom", utilisateur.getPrenom())
				.addValue("email", utilisateur.getEmail())
				.addValue("telephone", utilisateur.getTelephone())
				.addValue("rue", utilisateur.getRue())
				.addValue("code_postal", utilisateur.getCodePostal())
				.addValue("ville", utilisateur.getVille())
				.addValue("mot_de_passe", utilisateur.getMotDePasse())
				.addValue("credit", utilisateur.getCredit())
				.addValue("administrateur", utilisateur.isAdministrateur());

		namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"no_utilisateur"});
		utilisateur.setNoUtilisateur(keyHolder.getKeyAs(Integer.class));
	}
	@Override
	public void update(Utilisateur entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Utilisateur> findByPseudo(String pseudo) {
		String sql = SQL_SELECT + "where pseudo = ?";
		List<Utilisateur> utilisateurs = jdbcTemplate.query(sql, new UtilisateurRowMapper(), pseudo);

		if (utilisateurs.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(utilisateurs.get(0));
		}
	}
}

class UtilisateurRowMapper implements RowMapper<Utilisateur> {
	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
		Utilisateur utilisateur= new Utilisateur();

		utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
		utilisateur.setPseudo(rs.getString("pseudo"));
		utilisateur.setNom(rs.getString("nom"));
		utilisateur.setPrenom(rs.getString("prenom"));
		utilisateur.setEmail(rs.getString("email"));
		utilisateur.setTelephone(rs.getString("telephone"));
		utilisateur.setRue(rs.getString("rue"));
		utilisateur.setVille(rs.getString("ville"));
		utilisateur.setCodePostal(rs.getString("code_postal"));
		utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
		utilisateur.setCredit(rs.getInt("credit"));
		utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
		return utilisateur;
	}
}

