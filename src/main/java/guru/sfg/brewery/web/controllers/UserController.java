package guru.sfg.brewery.web.controllers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final GoogleAuthenticator googleAuthenticator;
    private static final String REG_URL = "user/register2fa";

    @GetMapping("/register2fa")
    public String register2fa(Model model){

        User user = getUser();
        String url= GoogleAuthenticatorQRGenerator.getOtpAuthURL("SFG", user.getUsername(),
                googleAuthenticator.createCredentials(user.getUsername()));

        log.debug("Google QR URL {} ", url);

        model.addAttribute("googleurl", url);

        return REG_URL;

    }

    @PostMapping
    public String confirm2fa(@RequestParam Integer verifyCode){

        User user = getUser();

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)){

            User savedUser = userRepository.findById(user.getId()).orElseThrow();
            savedUser.setUseGoogle2fa(true);
            userRepository.save(savedUser);
            return "index";
        }else{
            return REG_URL;
        }

    }

    private User getUser(){

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

}
