package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("name","Mr. Dev");
        model.addAttribute("message", "World!");
        return "index";
    }

    @GetMapping("/myProfile")
    public String getProfile(Model model) {
        model.addAttribute("name","Mr. Developer");
        return "profile";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("name", "Mr. Developer");
        return "settings";
    }

}
