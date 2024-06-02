package edu.atai.library.service;

import edu.atai.library.model.User;
import edu.atai.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).get();
    }

}
