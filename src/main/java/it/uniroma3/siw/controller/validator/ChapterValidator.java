package it.uniroma3.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chapter;

@Component
public class ChapterValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Chapter.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chapter chapter = (Chapter)target;
	}
	
}
