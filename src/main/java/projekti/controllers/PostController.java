package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.database.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private AccountService accountService;

    @PostMapping("/")
    public String post(@RequestParam String update) {
        Account user = accountService.getCurrentUser();
        Post post = new Post(user, update, LocalDateTime.now(),new ArrayList<>(), new ArrayList<>());
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/posts/{id}")
    public String like(@PathVariable Long id) {
        Account user = accountService.getCurrentUser();
        Post post = postRepository.getOne(id);
        LikePost like = new LikePost(post, user);
        likeRepository.save(like);
        return "redirect:/";
    }

    @PostMapping("/posts/{id}/unlike")
    public String unLike(@PathVariable Long id) {
        Account user = accountService.getCurrentUser();
        Post post = postRepository.getOne(id);
        LikePost like = likeRepository.findByPostAndUser(post, user);
        likeRepository.delete(like);
        return "redirect:/";
    }

}
