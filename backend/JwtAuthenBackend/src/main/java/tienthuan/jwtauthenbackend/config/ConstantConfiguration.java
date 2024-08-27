package tienthuan.jwtauthenbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:security.properties")
public class ConstantConfiguration {
    public final String SECRET_KEY;
    public final String USER_INTERFACE_URL;
    public final String USER_INTERFACE_METHOD_GET;
    public final String USER_INTERFACE_METHOD_POST;
    public final String CORS_ALLOWED_HEADER;
    public final Long CORS_MAX_AGE;
    public final String CORS_PATTERN;
    public final String API_ALL_AUTH;
    public final Long JWT_TOKEN_EXPIRATION;
    public final Long REFRESH_TOKEN_EXPIRATION;
    public final Boolean JWT_REVOKED_DISABLE;
    public final Boolean JWT_REVOKED_ENABLE;
    public final Boolean JWT_EXPIRED_DISABLE;
    public final Boolean JWT_EXPIRED_ENABLE;
    public final String HTTP_HEADER_AUTHORIZATION;
    public final String HTTP_HEADER_AUTHORIZATION_BEARER;
    public final String LOGOUT_HANDLER_URL;

    public ConstantConfiguration(
            @Value("${url.ui}") String user_interface_url,
            @Value("${url.ui.method.get}") String ui_method_get,
            @Value("${url.ui.method.post}") String ui_method_post,
            @Value("${url.cors.allowed-header}") String allowed_header,
            @Value("${url.cors.age}") Long max_age,
            @Value("${url.cors.pattern}") String cors_pattern,
            @Value("${api.url.all.auth}") String api_all_auth,
            @Value("${jwt.secret-key}") String key,
            @Value("${jwt.expiration}") Long jwt_token_expiration,
            @Value("${jwt.refresh-token.expiration}") Long refresh_token_expiration,
            @Value("${jwt.expired.disable}") Boolean jwt_expired_disable,
            @Value("${jwt.expired.enable}") Boolean jwt_expired_enable,
            @Value("${jwt.revoked.disable}") Boolean jwt_revoked_disable,
            @Value("${jwt.revoked.enable}") Boolean jwt_revoked_enable,
            @Value("${auth.header}") String http_header_authorization,
            @Value("${auth.header.bearer}") String http_header_authorization_bearer,
            @Value("${url.logout}") String logout
    ) {
        this.SECRET_KEY = key;
        this.USER_INTERFACE_URL = user_interface_url;
        this.USER_INTERFACE_METHOD_GET = ui_method_get;
        this.USER_INTERFACE_METHOD_POST = ui_method_post;
        this.CORS_ALLOWED_HEADER = allowed_header;
        this.CORS_MAX_AGE = max_age;
        this.CORS_PATTERN = cors_pattern;
        this.API_ALL_AUTH = api_all_auth;
        this.JWT_TOKEN_EXPIRATION = jwt_token_expiration;
        this.REFRESH_TOKEN_EXPIRATION = refresh_token_expiration;
        this.JWT_REVOKED_DISABLE = jwt_revoked_disable;
        this.JWT_REVOKED_ENABLE = jwt_revoked_enable;
        this.JWT_EXPIRED_DISABLE = jwt_expired_disable;
        this.JWT_EXPIRED_ENABLE = jwt_expired_enable;
        this.HTTP_HEADER_AUTHORIZATION = http_header_authorization;
        this.HTTP_HEADER_AUTHORIZATION_BEARER = http_header_authorization_bearer;
        this.LOGOUT_HANDLER_URL = logout;
    }

}
