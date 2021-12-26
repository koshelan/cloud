package netology.cloud.config;

import netology.cloud.exceptions.AuthenticationCloudException;
import netology.cloud.model.CurrentUser;
import netology.cloud.service.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final LoginService loginService;
    private final CurrentUser currentUser;

    public AuthInterceptor(LoginService loginService, CurrentUser currentUser) {
        this.loginService = loginService;
        this.currentUser = currentUser;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        try {
            var path = request.getRequestURI();
            if (path.endsWith("/login")) return true;

            var token = request.getHeaders("auth-token").nextElement().split(" ")[1];
            loginService.getUserId(token).ifPresentOrElse(currentUser::setId,
                    () -> {
                        throw new AuthenticationCloudException("user not authorised");
                    });
            return true;
        } catch (Exception e) {
            throw new AuthenticationCloudException("user not authorised");
        }
    }
}
