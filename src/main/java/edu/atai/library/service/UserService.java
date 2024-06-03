package edu.atai.library.service;

import edu.atai.library.exception.AppException;
import edu.atai.library.model.User;
import edu.atai.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(AppException::notFound);
    }

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = repository.findUserByLogin(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user : " + username);
        }
        return optional.get();
    }
}
