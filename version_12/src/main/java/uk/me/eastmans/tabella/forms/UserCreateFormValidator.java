package uk.me.eastmans.tabella.forms;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Validator;
import javax.validation.constraints.*;

/**
 * Created by meastman on 23/12/15.
 */
@ApplicationScoped
public class UserCreateFormValidator  {
//    private final UserService userService;

//    @Autowired
//    public UserCreateFormValidator(UserService userService) {
//        this.userService = userService;
//    }

    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

/*
    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
*/

}
