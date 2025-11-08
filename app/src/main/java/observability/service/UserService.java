package observability.service;

import observability.exceptions.RessourceNotFoundException;
import observability.model.User;
import observability.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password).orElseThrow(
                ()-> new RessourceNotFoundException("Mauvaise combinaison mail/mot de passe"));
    }
}
