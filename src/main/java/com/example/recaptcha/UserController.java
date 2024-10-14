package com.example.recaptcha;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Vérifie le reCAPTCHA
        if (!captchaService.verifyCaptcha(userDto.getCaptchaResponse())) {
            model.addAttribute("captchaError", "Captcha validation failed!");
            return "register";
        }


        model.addAttribute("successMessage", "Registration successful!");
        return "register";
    }

}
