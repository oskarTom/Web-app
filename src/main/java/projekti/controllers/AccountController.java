package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.database.Account;
import projekti.database.AccountRepository;

import java.util.ArrayList;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String url) {
        if(accountRepository.findByUsername(username) != null ||
           accountRepository.findByUrl(url) != null) {
            return "redirect:/signup";
        }

        Account account = new Account(username, passwordEncoder.encode(password), name, url, null,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        accountRepository.save(account);
        return "redirect:/";
    }
}
