package it.uniroma3.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.User;

/**
 * Validator for User
 */
@Component
public class UserValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 20;
    final Integer MIN_NAME_LENGTH = 4;

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String name = user.getName().trim();

        if (name.isEmpty())
            errors.rejectValue("name", "required");
        else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
            errors.rejectValue("name", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

}

