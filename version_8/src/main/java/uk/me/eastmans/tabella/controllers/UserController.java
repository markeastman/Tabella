package uk.me.eastmans.tabella.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.forms.UserCreateForm;
import uk.me.eastmans.tabella.forms.UserCreateFormValidator;
import uk.me.eastmans.tabella.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;
    private UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserCreateFormValidator(UserCreateFormValidator userCreateFormValidator) {
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @RequestMapping("/users")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder)
    {
        binder.addValidators(userCreateFormValidator);
    }

    @RequestMapping("/user/{id}")
    public String getUserPage(@PathVariable Long id, Model model) {
        Optional<User> u = userService.getUserById(id);
        model.addAttribute("user",u.get());
        return "user";
    }

    @RequestMapping(value = "/user/create",method = RequestMethod.GET)
    public String getUserCreatePage(Model model)
    {
        model.addAttribute("form", new UserCreateForm());
        return "userCreate";
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userCreate";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "userCreate";
        }
        return "redirect:/users";
    }
}