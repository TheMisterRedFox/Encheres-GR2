package fr.eni.encheres.bll.utilisateur;

import java.util.List;
import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.utilisateur.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurRepository utilisateurRepo;
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepo) {
		this.utilisateurRepo = utilisateurRepo;
	}
	
	@Override
	public List<Utilisateur> findAll() {
		return utilisateurRepo.findAll();
	}

	@Override
	public Optional<Utilisateur> findById(int id) {
		return utilisateurRepo.findById(id);
	}

	@Override
	public void update(Utilisateur entity) {
		utilisateurRepo.update(entity);
	}

	@Override
	public void add(Utilisateur entity) {
		utilisateurRepo.add(entity);
	}

	@Override
	public void save(Utilisateur entity) {
		//TODO faire conditions
		this.add(entity);
	}

	@Override
	public void delete(int id) {
		utilisateurRepo.delete(id);
	}

}