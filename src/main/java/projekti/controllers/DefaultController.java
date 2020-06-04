package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Account;
import projekti.AccountRepository;
import projekti.Connection;
import projekti.ConnectionRepository;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        configureHeader(model);
        model.addAttribute("message", "World!");
        return "index";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        Account myaccount = configureHeader(model);
        Account theiraccount = accountRepository.findByUrl(id);
        if (theiraccount == null) {
            return "index";
        }
        model.addAttribute("user", theiraccount);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);
        if (connection == null) {
            model.addAttribute("added", false);
        } else {
            model.addAttribute("added", true);
        }

        return "profile";
    }

    @PostMapping("/user/{id}/add")
    public String addToContacts(@PathVariable String id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account myaccount = accountRepository.findByUsername(username);
        Account theiraccount = accountRepository.findByUrl(id);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);

        if (connection == null) {
            Connection newConnect = new Connection(myaccount, theiraccount);
            connectionRepository.save(newConnect);
        }

        return "redirect:/user/"+id;
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        configureHeader(model);
        return "settings";
    }

    public Account configureHeader(Model model){
        Account myaccount;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            myaccount = accountRepository.findByUsername(username);
        } catch (Exception e) {
            myaccount = new Account("developer", "password", "LOGIN_NOT_FOUND", "tom", new ArrayList<>());
        }
        model.addAttribute("me", myaccount);
        return myaccount;
    }
}
