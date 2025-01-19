package mk.finki.ukim.mk.mseanalyiser.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @GetMapping
    public String getRegisterPage() {
        // Return the name of the Thymeleaf template that will be used to render the register page
        return "register";
    }
    @PostMapping
    public String register()
    {
        return "redirect:/login";
    }
}
