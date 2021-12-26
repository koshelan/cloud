package netology.cloud.controller;

import lombok.AllArgsConstructor;
import netology.cloud.exceptions.AuthenticationCloudException;
import netology.cloud.model.LoginCommand;
import netology.cloud.model.LoginResponse;
import netology.cloud.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String authToken) {
        loginService.logout(getToken(authToken));
    }

    @PostMapping("/login")
    public LoginResponse authorization(@RequestBody LoginCommand loginCommand) {
        return loginService.authorization(loginCommand.login(), loginCommand.password());
    }

    private String getToken(String token) {
        try {
            return token.split(" ")[1];
        } catch (Exception e) {
            throw new AuthenticationCloudException("user not authorised");
        }
    }
}
