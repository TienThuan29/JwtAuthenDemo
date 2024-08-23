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

    public ConstantConfiguration(
            @Value("${key}") String key,
            @Value("${url.ui}") String user_interface_url,
            @Value("${url.ui.method.get}") String ui_method_get,
            @Value("${url.ui.method.post}") String ui_method_post,
            @Value("${url.cors.allowed-header}") String allowed_header,
            @Value("${url.cors.age}") Long max_age,
            @Value("${url.cors.pattern}") String cors_pattern,
            @Value("${api.url.all.auth}") String api_all_auth
    ) {
        this.SECRET_KEY = key;
        this.USER_INTERFACE_URL = user_interface_url;
        this.USER_INTERFACE_METHOD_GET = ui_method_get;
        this.USER_INTERFACE_METHOD_POST = ui_method_post;
        this.CORS_ALLOWED_HEADER = allowed_header;
        this.CORS_MAX_AGE = max_age;
        this.CORS_PATTERN = cors_pattern;
        this.API_ALL_AUTH = api_all_auth;
    }

}
