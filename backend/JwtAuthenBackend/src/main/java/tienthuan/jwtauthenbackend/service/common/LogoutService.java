package tienthuan.jwtauthenbackend.service.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import tienthuan.jwtauthenbackend.config.ConstantConfiguration;
import tienthuan.jwtauthenbackend.model.Token;
import tienthuan.jwtauthenbackend.repository.TokenRepository;

@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ConstantConfiguration constant;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(constant.HTTP_HEADER_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(constant.HTTP_HEADER_AUTHORIZATION_BEARER))
            return;
        String jwt = authHeader.substring(constant.HTTP_HEADER_AUTHORIZATION_BEARER.length());
        Token token = tokenRepository.findByToken(jwt).orElseThrow();
        if(token != null) {
            token.setExpired(constant.JWT_EXPIRED_ENABLE);
            token.setRevoked(constant.JWT_REVOKED_ENABLE);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
    }
}
