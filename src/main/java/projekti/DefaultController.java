package projekti;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String helloWorld(Model model, Principal principal) {
        String username = principal.getName();
        Account myaccount = accountRepository.findByUsername(username);
        if (myaccount == null) {
            myaccount = new Account("developer", "password", "Mr. dev", "tom");
        }
        model.addAttribute("me",myaccount);
        model.addAttribute("message", "World!");
        return "index";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        Account account = accountRepository.findByUrl(id);
        if (account == null) {
            return "index";
        }
        model.addAttribute("me",account);
        model.addAttribute("name", account.getName());
        return "profile";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("name", "Mr. Developer");
        return "settings";
    }

}
