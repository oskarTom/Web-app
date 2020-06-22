package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projekti.database.*;

import javax.tools.FileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PraiseRepository praiseRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        Account myaccount = accountService.configureHeader(model);
        model.addAttribute("me", myaccount);

        List<Account> posters = connectionService.getConfirmed(myaccount);
        posters.add(myaccount);

        List<Post> posts = new ArrayList<>();
        posts.addAll(postRepository.findByPosterIn(posters));
        Pageable pageable = PageRequest.of(0,25, Sort.by("time").ascending());

        List<Post> liked = new ArrayList<>();
        for (Post post : posts) {
            if (likeRepository.findByPostAndUser(post, myaccount) != null) {
                liked.add(post);
            }
        }
        model.addAttribute("liked", liked);
        model.addAttribute("posts", posts);

        return "index";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        Account myaccount = accountService.configureHeader(model);
        Account theiraccount = accountService.getByUrl(id);
        if (theiraccount == null) {
            return "index";
        }
        model.addAttribute("user", theiraccount);
        model.addAttribute("added", connectionService.getConnectionStatus(myaccount, theiraccount));

        Pageable pageable = PageRequest.of(0,3, Sort.by("praises").descending());
        model.addAttribute("skills", skillRepository.findByUser(theiraccount, pageable));
        model.addAttribute("allskills", skillRepository.findByUser(theiraccount));

        List<Praise> praises = praiseRepository.findByUser(myaccount);
        List<Skill> praised = new ArrayList<>();
        for (Praise praise : praises) {
            praised.add(praise.getSkill());
        }
        model.addAttribute("praised", praised);

        return "profile";
    }

    @PostMapping("/user/{id}/profilepicture")
    public String updateProfile(@PathVariable String id,
                                @RequestParam("file") MultipartFile file) throws IOException {
        accountService.setProfilePicture(file);
        return "redirect:/user/" + id;
    }

    @GetMapping(path = "/user/{id}/profilepicture", produces = "image/png")
    @ResponseBody
    public byte[] getProfilePicture(@PathVariable String id){
        return accountService.getProfilePic(id);
    }

    @PostMapping("/user/{id}/add")
    public String addToContacts(@PathVariable String id) {
        accountService.addToContacts(id);
        return "redirect:/user/"+id;
    }

    @PostMapping("/contacts/{id}/respond")
    public String acceptRequest(@PathVariable String id,
                                @RequestParam(value = "action") String action) {
        if (action.equals("Accept")) {
            accountService.addToContacts(id);
        }

        if (action.equals("Decline")) {
            accountService.removeFromContacts(id);
        }

        return "redirect:/contacts";
    }

    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable String id) {
        accountService.removeFromContacts(id);
        return "redirect:/contacts";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        Account myaccount = accountService.configureHeader(model);
        model.addAttribute("person", myaccount);
        return "settings";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        Account myaccount = accountService.configureHeader(model);
        connectionService.setConfirmedAndRequests(model, myaccount);
        return "contacts";
    }
}
