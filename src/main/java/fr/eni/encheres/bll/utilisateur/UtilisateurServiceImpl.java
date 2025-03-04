package fr.eni.encheres.bll.utilisateur;

import java.util.List;
import java.util.Optional;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.utilisateur.UtilisateurRepository;
import fr.eni.encheres.exceptions.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurRepository utilisateurRepo;
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepo) {
		this.utilisateurRepo = utilisateurRepo;
		//this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public List<Utilisateur> findAll() {
		return utilisateurRepo.findAll();
	}

	@Override
	public Optional<Utilisateur> findById(int noUtilisateur) {
		return utilisateurRepo.findById(noUtilisateur);
	}

	@Override
	public void update(Utilisateur utilisateur) {
		utilisateurRepo.update(utilisateur);
	}

	@Override
	public void add(Utilisateur utilisateur) throws UsernameAlreadyExistsException {
		if(this.findByPseudo(utilisateur.getPseudo()).isPresent()){
			throw new UsernameAlreadyExistsException();
		} else {
			utilisateur.setCredit(100);
			utilisateur.setAdministrateur(false);
			utilisateurRepo.add(utilisateur);
		}
	}

	@Override
	public void save(Utilisateur utilisateur) throws UsernameAlreadyExistsException {
		if(utilisateur.getNoUtilisateur() != 0) {
			this.update(utilisateur);
		} else {
			this.add(utilisateur);
		}
	}

	@Override
	public void delete(int id) {
		utilisateurRepo.delete(id);
	}

	@Override
	public Optional<Utilisateur> findByPseudo(String pseudo) {
		return utilisateurRepo.findByPseudo(pseudo);
	}
}