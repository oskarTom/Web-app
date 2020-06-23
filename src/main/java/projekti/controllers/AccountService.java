package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import projekti.database.Account;
import projekti.database.AccountRepository;
import projekti.database.Connection;
import projekti.database.ConnectionRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            myaccount = new Account("developer", "password", "LOGIN_NOT_FOUND", "tom", null,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        model.addAttribute("me", myaccount);
        return myaccount;
    }

    public void setProfilePicture(MultipartFile file) throws IOException {
        Account myaccount = getCurrentUser();
        myaccount.setProfilePic(file.getBytes());
        accountRepository.save(myaccount);
    }

    public byte[] getProfilePic(String url){
        return accountRepository.findByUrl(url).getProfilePic();
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
        if (connection != null) connectionRepository.delete(connection);
        connection = connectionRepository.findByUserAndFriend(theiraccount, myaccount);
        if (connection != null) connectionRepository.delete(connection);
    }

    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return accountRepository.findByUsername(username);
    }

    public Account getByUrl(String url) {
        return accountRepository.findByUrl(url);
    }

    public List<Account> getByName(String name) {
        return accountRepository.findByName(name);
    }
}
