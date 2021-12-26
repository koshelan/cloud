package netology.cloud.service;

import netology.cloud.model.CurrentUser;
import netology.cloud.model.User;
import netology.cloud.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    private static String token = "";
    private static final Long ID = 999L;
    private static final CurrentUser currentUser = new CurrentUser(String.valueOf(ID));
    private static final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private static final LoginService loginService = new LoginService(currentUser,userRepository);

    @BeforeAll
    static void preparation(){
        var user = new User("l","p");
        user.setId(ID);
        when(userRepository.findByLoginAndPassword("l","p")).thenReturn(Optional.of(user));
        token = loginService.authorization("l","p").token();
    }

    @Test
    void authorization() {
        var user = new User("log","pas");
        var id = 1L;
        user.setId(id);
        when(userRepository.findByLoginAndPassword("log","pas")).thenReturn(Optional.of(user));
        var t = loginService.authorization("log","pas").token();
        assertEquals(String.valueOf(id),loginService.getUserId(t).get());
    }

    @Test
    void logout() {
        loginService.logout(token);
        assertEquals(null,currentUser.getId());
        assertTrue(!loginService.getUserId(token).isPresent());
        preparation();
    }

    @Test
    void getUserId() {
        assertEquals(String.valueOf(ID),loginService.getUserId(token).get());
    }
}