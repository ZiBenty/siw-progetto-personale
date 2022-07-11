package it.uniroma3.siw.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Chapter;
import it.uniroma3.siw.model.OCharacter;

public interface ChapterRepository extends CrudRepository<Chapter, Long>{
	
	public Set<Chapter> findByCharacter(OCharacter character);
}