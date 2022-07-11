package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.ChapterValidator;
import it.uniroma3.siw.model.Chapter;
import it.uniroma3.siw.model.OCharacter;
import it.uniroma3.siw.service.ChapterService;
import it.uniroma3.siw.service.OCharacterService;

@Controller
public class ChapterController {
	@Autowired
	private ChapterService chapterService;
	@Autowired
	private OCharacterService characterService;
	@Autowired
	private ChapterValidator chapterValidator;
	
	//salva e ritorna il capitolo salvato
	@PostMapping("/chapter/{charId}")
	public String newChapter(@PathVariable("charId") Long charId, 
			@Valid @ModelAttribute("chapter") Chapter chapter, 
			BindingResult bindingResult, Model model) {
		OCharacter character = this.characterService.findById(charId);
		this.chapterValidator.validate(chapter, bindingResult);
		if(!bindingResult.hasErrors()) {
			if(chapter.getId() == null) {
				character.addChapter(chapter);
				this.chapterService.save(chapter);
				model.addAttribute("chapter", chapter);
			} else {
				Chapter toModify = this.chapterService.findById(chapter.getId());
				this.chapterService.edit(toModify, chapter);
				model.addAttribute("chapter", toModify);
			}
			return "chapter.html";
		} else {
			model.addAttribute("character", character);
			return "chapterForm.html";
		}
	}
	
	//richiede la form per aggiungere un capitolo ad un personaggio
	@GetMapping("/chapter/new/{charId}")
	public String getChapterForm(@PathVariable("charId") Long charId, Model model) {
		model.addAttribute("chapter", new Chapter());
		model.addAttribute("character", this.characterService.findById(charId));
		return "chapterForm.html";
	}
	
	//richiede la form per modificare un capitolo di un personaggio
	@GetMapping("/chapter/edit/{id}")
	public String editChapter(@PathVariable("id") Long id, Model model) {
		Chapter chapter = this.chapterService.findById(id);
		model.addAttribute("chapter", chapter);
		model.addAttribute("character", chapter.getCharacter());
		return "chapterForm.html";
	}
	
	//ritorna i capitoli della storia di un personaggio
	@GetMapping("/chapters/{charId}")
	public String getCharStory(@PathVariable("charId") Long charId, Model model) {
		OCharacter character = this.characterService.findById(charId);
		model.addAttribute("character", character);
		model.addAttribute("listChapters", character.getStory());
		return "characterChapters.html";
	}
	
	//richiede il capitolo con lo specifico id
	@GetMapping("/chapter/{id}")
	public String getChapter(@PathVariable("id") Long id, Model model) {
		Chapter chapter = this.chapterService.findById(id);
		model.addAttribute("chapter", chapter);
		return "chapter.html";
	}
	
	@GetMapping("chapter/delete/{id}")
	public String deleteChapter(@PathVariable("id") Long id, Model model) {
		Chapter chapter = this.chapterService.findById(id);
		Long charId = chapter.getCharacter().getId();
		chapter.getCharacter().removeChapter(chapter);
		this.chapterService.deleteById(id);
		return "redirect:/character/" + charId;
	}
}
