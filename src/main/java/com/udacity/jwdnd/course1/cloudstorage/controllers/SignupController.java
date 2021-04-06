package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String errorMessage = null;

        if(!userService.isUsernameAvailable(user.getUsername())) {
            errorMessage = "The username already exists!";
        }

        if(errorMessage == null) {
            int userCreated = userService.createUser(user);

            if(userCreated < 0) {
                errorMessage = "Error occurred while signing up a new user. Please try again!";
            }

            if(userCreated > 0) {
                redirectAttributes.addFlashAttribute("successfulSignup",true);
                return "redirect:/login";
            }
        }

        if(errorMessage == null) {
            model.addAttribute("successfulSignup", true);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "signup";
    }
}
