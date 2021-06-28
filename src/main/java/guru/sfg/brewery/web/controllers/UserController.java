package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/register2fa")
    public String register2fa(Model model){

        model.addAttribute("googleurl", "todo");

        return "user/register2fa";
    }

    @PostMapping("/confirm1fa")
    public String confirm2fa(@RequestParam Integer verifyCode){


        return "index";
    }


}
