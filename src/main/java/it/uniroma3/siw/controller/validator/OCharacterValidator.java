package it.uniroma3.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.OCharacter;

@Component
public class OCharacterValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return OCharacter.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OCharacter character = (OCharacter)target;
	}

}
