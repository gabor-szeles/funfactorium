package org.funfactorium.security.password;


import org.funfactorium.user.User;
import org.funfactorium.user.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegistrationDto userDto = (UserRegistrationDto) obj;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
