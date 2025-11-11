package observability.controller;


import observability.DTO.LoginDTO;
import observability.model.User;
import observability.repository.UserRepository;
import observability.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public User login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User requestUser) {
        User user =  userService.signUp(new User(requestUser.getName(), requestUser.getFirstname(), requestUser.getAge(), requestUser.getEmail(), requestUser.getPassword()));
        return ResponseEntity.ok("User created : " + user.toString());
    }
}
