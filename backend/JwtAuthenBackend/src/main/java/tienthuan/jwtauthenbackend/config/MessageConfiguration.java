package tienthuan.jwtauthenbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
public class MessageConfiguration {

    public final String ERROR_USERNAME_NOTFOUND;
    public final String ERROR_USERNAME_PASSWORD_INVALID;
    public final String ERROR_ROLE_NOTFOUND;

    public MessageConfiguration(
            @Value("${message.error.username-pass-incorrect}") String err_username_password_invalid,
            @Value("${message.error.role.notfound}") String err_role_notfound,
            @Value("${message.error.username.notfound}") String err_username_notfound
    ) {
        this.ERROR_USERNAME_PASSWORD_INVALID = err_username_password_invalid;
        this.ERROR_ROLE_NOTFOUND = err_role_notfound;
        this.ERROR_USERNAME_NOTFOUND = err_username_notfound;
    }

}
