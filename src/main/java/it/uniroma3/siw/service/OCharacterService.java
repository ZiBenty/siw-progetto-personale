package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.OCharacter;
import it.uniroma3.siw.repository.OCharacterRepository;

@Service
public class OCharacterService {
	@Autowired
	private OCharacterRepository characterRepository;
	
	@Transactional
	public void save(OCharacter character) {
		this.characterRepository.save(character);
	}
	
	@Transactional
	public void edit(OCharacter original, OCharacter modified) {
		original.setName(modified.getName());
		original.setDescription(modified.getDescription());
		if(modified.getPic() != null)
			original.setPic(modified.getPic());
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.characterRepository.deleteById(id);
	}
	
	public OCharacter findById(Long id) {
		return this.characterRepository.findById(id).get();
	}
	
	public List<OCharacter> findAll(){
		List<OCharacter> allCharacters = new ArrayList<OCharacter>();
		for (OCharacter c: this.characterRepository.findAll()) {
			allCharacters.add(c);
		}
		return allCharacters;
	}

	public void delete(OCharacter character) {
		this.characterRepository.delete(character);
	}
}
