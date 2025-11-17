package observability.controller;


import observability.DTO.LoginDTO;
import observability.model.User;
import observability.repository.UserRepository;
import observability.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<User> login(@RequestBody LoginDTO loginDTO) {
        User user =  userService.login(loginDTO.getEmail(), loginDTO.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/signup")
    //User en requestBody plutôt que de faire un DTO dédié
    public ResponseEntity<User> signup(@RequestBody User requestUser) {
        User user =  userService.signUp(new User(requestUser.getName(), requestUser.getFirstname(), requestUser.getAge(), requestUser.getEmail(), requestUser.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
