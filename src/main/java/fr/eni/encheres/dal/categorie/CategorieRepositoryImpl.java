package fr.eni.encheres.dal.categorie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieRepositoryImpl implements CategorieRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	public CategorieRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Categorie> findAll() {
		String sql = "select libelle, no_categorie from categories";
		List<Categorie> categories = jdbcTemplate.query(sql, new CategorieRowMapper());
		
		return categories;
	}

	@Override
	public Optional<Categorie> findById(int id) {
		String sql = "select libelle, no_categorie from categories where no_categorie = ?";
		Categorie categorie = jdbcTemplate.queryForObject(sql, new CategorieRowMapper(), id);
		
		return Optional.ofNullable(categorie);
	}

	@Override
	public void add(Categorie entity) {

	}

	@Override
	public void update(Categorie entity) {

	}

	@Override
	public void delete(int id) {

	}

}

class CategorieRowMapper implements RowMapper<Categorie> {
    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Categorie categorie= new Categorie();
    	categorie.setNoCategorie(rs.getInt("no_categorie"));
    	categorie.setLibelle(rs.getString("libelle"));
        return categorie;
    }
}
