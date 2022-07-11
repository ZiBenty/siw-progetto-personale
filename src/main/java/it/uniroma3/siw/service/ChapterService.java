package it.uniroma3.siw.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Chapter;
import it.uniroma3.siw.model.OCharacter;
import it.uniroma3.siw.repository.ChapterRepository;

@Service
public class ChapterService {
	@Autowired
	private ChapterRepository chapterRepository;
	
	@Transactional
	public void save(Chapter chapter) {
		this.chapterRepository.save(chapter);
	}
	
	@Transactional
	public void edit(Chapter original, Chapter modified) {
		original.setTitle(modified.getTitle());
		original.setContent(modified.getContent());
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.chapterRepository.deleteById(id);
	}
	
	public Chapter findById(Long id) {
		return this.chapterRepository.findById(id).get();
	}
	
	public Set<Chapter> findAll(){
		Set<Chapter> allChapters = new HashSet<Chapter>();
		for (Chapter c: this.chapterRepository.findAll()) {
			allChapters.add(c);
		}
		return allChapters;
	}
	
	public Set<Chapter> findByCharacter(OCharacter character){
		return this.chapterRepository.findByCharacter(character);
	}
}
