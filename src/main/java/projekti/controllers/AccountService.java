package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekti.database.Account;
import projekti.database.AccountRepository;
import projekti.database.Connection;
import projekti.database.ConnectionRepository;

import java.util.ArrayList;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public Account configureHeader(Model model){
        Account myaccount;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            myaccount = accountRepository.findByUsername(username);
        } catch (Exception e) {
            myaccount = new Account("developer", "password", "LOGIN_NOT_FOUND", "tom", new ArrayList<>());
        }
        model.addAttribute("me", myaccount);
        return myaccount;
    }

    public void addToContacts(String url){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account myaccount = accountRepository.findByUsername(username);
        Account theiraccount = accountRepository.findByUrl(url);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);

        if (connection == null) {
            Connection newConnect = new Connection(myaccount, theiraccount);
            connectionRepository.save(newConnect);
        }
    }

    public void removeFromContacts(String url){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account myaccount = accountRepository.findByUsername(username);
        Account theiraccount = accountRepository.findByUrl(url);
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);
        connectionRepository.delete(connection);
        connection = connectionRepository.findByUserAndFriend(theiraccount, myaccount);
        connectionRepository.delete(connection);
    }
}
