package observability.service;

import observability.exceptions.ResourceConflictException;
import observability.exceptions.ResourceNotFoundException;
import observability.model.User;
import observability.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password).orElseThrow(
                ()-> new ResourceNotFoundException("Mauvaise combinaison mail/mot de passe"));
    }

    public User signUp(User user) {
        Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
        if (maybeUser.isPresent()) {
            throw new ResourceConflictException("signup() : Un compte avec ce mail existe déjà !");
        }
        return userRepository.insert(user);
    }
}
