package com.hoaxifly.ws.User.Validation;

import com.hoaxifly.ws.User.User;
import com.hoaxifly.ws.User.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User inDB = userRepository.findByEmail(value);
        if (inDB != null) {
            return false;
        }
        return true;

        //return inDB == null
    }
}
