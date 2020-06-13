package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.database.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


}
