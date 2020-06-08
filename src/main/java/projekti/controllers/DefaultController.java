package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.database.Account;
import projekti.database.AccountRepository;
import projekti.database.Connection;
import projekti.database.ConnectionRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @GetMapping("/")
    public String helloWorld(Model model) {
        Account myaccount = accountService.configureHeader(model);
        model.addAttribute("message", myaccount.getName());
        return "index";
    }

    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable String id, Model model) {
        Account myaccount = accountService.configureHeader(model);
        Account theiraccount = accountRepository.findByUrl(id);
        if (theiraccount == null) {
            return "index";
        }
        model.addAttribute("user", theiraccount);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);
        if (connection == null) {
            model.addAttribute("added", 0);
        } else if(connectionRepository.findByUserAndFriend(theiraccount, myaccount) == null){
            model.addAttribute("added", 1);
        } else {
            model.addAttribute("added", 2);
        }

        return "profile";
    }

    @PostMapping("/user/{id}/add")
    public String addToContacts(@PathVariable String id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account myaccount = accountRepository.findByUsername(username);
        Account theiraccount = accountRepository.findByUrl(id);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);

        if (connection == null) {
            Connection newConnect = new Connection(myaccount, theiraccount);
            connectionRepository.save(newConnect);
        }

        return "redirect:/user/"+id;
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        accountService.configureHeader(model);
        return "settings";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        Account myaccount = accountService.configureHeader(model);

        List<Connection> connections = connectionRepository.findByFriend(myaccount);
        List<Account> requests = new ArrayList<>();
        for (Connection connection : connections) {
            requests.add(connection.getUser());
        }

        List<Account> confirmed = new ArrayList<>();
        connections = connectionRepository.findByUser(myaccount);
        for (Connection connection : connections) {
            if (requests.contains(connection.getFriend())) {
                confirmed.add(connection.getFriend());
                requests.remove(connection.getFriend());
            }
        }

        model.addAttribute("confirmed", confirmed);
        model.addAttribute("requests", requests);
        return "contacts";
    }
}
