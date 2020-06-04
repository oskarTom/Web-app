package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.AccountRepository;

@Service
public class ProfileService {

    @Autowired
    private AccountRepository accountRepository;


}
