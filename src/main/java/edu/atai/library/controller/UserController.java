package edu.atai.library.controller;

import edu.atai.library.model.User;
import edu.atai.library.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public GetUserResponse getUser(Principal principal) {
        String login = principal.getName();
        User user = service.loadUserByUsername(login);
        return new GetUserResponse(
            user.getId(),
            user.getFullName(),
            List.of(user.getRole().name())
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GetUserResponse {
        private Long id;
        private String fullName;
        private List<String> roles;
    }

}
