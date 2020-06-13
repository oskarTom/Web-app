package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekti.database.Account;
import projekti.database.Connection;
import projekti.database.ConnectionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<Account> getConfirmed(Account account) {
        List<Account> posters = new ArrayList<>();
        List<Connection> connections = connectionRepository.findByFriend(account);
        List<Account> requests = new ArrayList<>();
        for (Connection connection : connections) {
            requests.add(connection.getUser());
        }

        connections = connectionRepository.findByUser(account);
        for (Connection connection : connections) {
            if (requests.contains(connection.getFriend())) {
                posters.add(connection.getFriend());
            }
        }

        return posters;
    }

    public void setConfirmedAndRequests(Model model, Account account) {
        List<Connection> connections = connectionRepository.findByFriend(account);
        List<Account> requests = new ArrayList<>();
        for (Connection connection : connections) {
            requests.add(connection.getUser());
        }

        List<Account> confirmed = new ArrayList<>();
        connections = connectionRepository.findByUser(account);
        for (Connection connection : connections) {
            if (requests.contains(connection.getFriend())) {
                confirmed.add(connection.getFriend());
                requests.remove(connection.getFriend());
            }
        }

        model.addAttribute("confirmed", confirmed);
        model.addAttribute("requests", requests);
    }

    public int getConnectionStatus(Account myaccount, Account theiraccount){
        Connection connection = connectionRepository.findByUserAndFriend(myaccount, theiraccount);
        if (connection == null) {
            return 0;
        } else if(connectionRepository.findByUserAndFriend(theiraccount, myaccount) == null){
            return 1;
        } else {
            return 2;
        }
    }
}
