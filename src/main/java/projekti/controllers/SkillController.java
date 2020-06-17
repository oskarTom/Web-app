package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.database.*;

import java.util.ArrayList;

@Controller
public class SkillController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PraiseRepository praiseRepository;


    @PostMapping("/user/{url}/skill")
    public String addSkill(@PathVariable String url,
                           @RequestParam String skill) {
        Account user = accountRepository.findByUrl(url);
        skillRepository.save(new Skill(user, skill, new ArrayList<>()));
        return "redirect:/user/{url}";
    }


}
