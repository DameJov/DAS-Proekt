package mk.finki.ukim.mk.mseanalyiser.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage() {
        // Return the name of the Thymeleaf template that will be used to render the login page
        return "login";
    }
    @PostMapping
    public String login()
    {
        return "redirect:/codes";
    }

}
