package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.database.Account;
import projekti.database.Post;
import projekti.database.PostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @PostMapping("/")
    public String post(@RequestParam String update) {
        Account user = accountService.getCurrentUser();
        Post post = new Post(user, update, LocalDateTime.now(),new ArrayList<>(), new ArrayList<>());
        postRepository.save(post);
        return "redirect:/";
    }



}
