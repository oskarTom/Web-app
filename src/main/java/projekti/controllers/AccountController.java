package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Account;
import projekti.AccountRepository;

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

        Account account = new Account(username,
                                      passwordEncoder.encode(password),
                                      name, url);

        System.out.println("***"+accountRepository.save(account)+"***");
        System.out.println("***"+account+"***");

        return "redirect:/";
    }
}
