package projekti;

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

import java.security.Principal;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        configureHeader(model);
        model.addAttribute("message", "World!");
        return "index";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        configureHeader(model);
        Account account = accountRepository.findByUrl(id);
        if (account == null) {
            return "index";
        }
        model.addAttribute("name", account.getName());
        return "profile";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        configureHeader(model);
        return "settings";
    }

    public void configureHeader(Model model){
        Account myaccount;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            myaccount = accountRepository.findByUsername(username);
        } catch (Exception e) {
            myaccount = new Account("developer", "password", "LOGIN_NOT_FOUND", "tom");
        }
        model.addAttribute("me", myaccount);
    }
}
