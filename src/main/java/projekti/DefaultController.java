package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
