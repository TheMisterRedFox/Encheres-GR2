package fr.eni.encheres.categorie;

import fr.eni.encheres.bll.categorie.CategorieService;
import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.CategoryAlreadyExistsException;
import fr.eni.encheres.exceptions.UsernameAlreadyExistsException;
import fr.eni.encheres.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategorieServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {

        jdbcTemplate.update("DELETE FROM CATEGORIES WHERE libelle = ?", "CategorieTestUnitaire");

        jdbcTemplate.update("INSERT INTO CATEGORIES (libelle) VALUES (?)", "CategorieTestUnitaire");
    }

    @Test
    void selectCategories_ShouldHaveAlmostOne() {
        assertNotNull(categorieService.findAll());
    }

    @Test
    void createCategorieWithSameLibelle_ShouldThrowException() {
        Categorie categorie = new Categorie();
        categorie.setLibelle("CategorieTestUnitaire");

        assertThrows(CategoryAlreadyExistsException.class, () -> categorieService.save(categorie));
    }
}
