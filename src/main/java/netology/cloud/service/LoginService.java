package netology.cloud.service;

import netology.cloud.exceptions.AuthenticationCloudException;
import netology.cloud.model.CurrentUser;
import netology.cloud.model.LoginResponse;
import netology.cloud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private final Map<String, String> authorisedUsers = new ConcurrentHashMap<>();

    public LoginService(CurrentUser currentUser, UserRepository userRepository) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }


    public LoginResponse authorization(String login, String password) {
        var user = userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new AuthenticationCloudException("user not found"));
        var token = UUID.randomUUID().toString();
        authorisedUsers.put(token, String.valueOf(user.getId()));
        return new LoginResponse(token);
    }

    public void logout(String authToken) {
        authorisedUsers.remove(authToken);
        currentUser.setId(null);
    }

    public Optional<String> getUserId(String token) {
        return Optional.ofNullable(authorisedUsers.get(token));
    }
}
