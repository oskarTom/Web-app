package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {

    @GetMapping("*")
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

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

}
