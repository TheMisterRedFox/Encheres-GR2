package fr.eni.encheres.dal.retrait;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Retrait;

@Repository
public class RetraitRepositoryImpl implements RetraitRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	
	public RetraitRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Retrait> findAll() {
		return null;
	}

	@Override
	public Optional<Retrait> findById(int id) {
		return Optional.empty();
	}

	@Override
	public void add(Retrait newRetrait) {
		String sql = "insert into retraits (no_article, rue, code_postal, ville) values (:noArticle, :rue, :codePostal, :ville)";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", newRetrait.getArticle().getNoArticle())
		      .addValue("rue", newRetrait.getRue())
		      .addValue("codePostal", newRetrait.getCodePostal())
		      .addValue("ville", newRetrait.getVille());
		
		namedParameterJdbcTemplate.update(sql, params);
	}

	@Override
	public void update(Retrait entity) {

	}

	@Override
	public void delete(int id) {

	}

}
