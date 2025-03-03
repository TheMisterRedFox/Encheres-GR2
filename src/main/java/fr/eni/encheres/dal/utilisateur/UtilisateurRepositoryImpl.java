package fr.eni.encheres.dal.utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.stereotype.Repository;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Utilisateur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Optional<Utilisateur> findById(int id) {/*
		String sql = "SELECT utilisateurs WHERE no_utilisateur = ?";
		Utilisateur user = jdbcTemplate.queryForObject(sql id);
		*/
		return Optional.empty();
	}
	
	@Override
	public void add(Utilisateur entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Utilisateur entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}

