package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Chapter;
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
	
	public List<Chapter> findAll(){
		List<Chapter> allChapters = new ArrayList<Chapter>();
		for (Chapter c: this.chapterRepository.findAll()) {
			allChapters.add(c);
		}
		return allChapters;
	}
}
