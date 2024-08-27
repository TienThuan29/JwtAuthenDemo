package tienthuan.jwtauthenbackend.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tienthuan.jwtauthenbackend.config.ConstantConfiguration;
import tienthuan.jwtauthenbackend.config.MessageConfiguration;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationRequest;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationResponse;
import tienthuan.jwtauthenbackend.dto.auth.RegisterRequest;
import tienthuan.jwtauthenbackend.dto.info.UserDTO;
import tienthuan.jwtauthenbackend.exception.def.NotFoundException;
import tienthuan.jwtauthenbackend.exception.def.TokenIsInvalidException;
import tienthuan.jwtauthenbackend.exception.def.UsernameOrPasswordInvalidException;
import tienthuan.jwtauthenbackend.model.Token;
import tienthuan.jwtauthenbackend.model.TokenType;
import tienthuan.jwtauthenbackend.model.User;
import tienthuan.jwtauthenbackend.repository.RoleRepository;
import tienthuan.jwtauthenbackend.repository.TokenRepository;
import tienthuan.jwtauthenbackend.repository.UserRepository;
import tienthuan.jwtauthenbackend.service.jwt.JwtService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final ConstantConfiguration constant;

    private final MessageConfiguration messageConfig;


    private void saveToken(User user, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(constant.JWT_EXPIRED_DISABLE)
                .revoked(constant.JWT_REVOKED_DISABLE)
                .user(user)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllOldUserToken(User user) {
        List<Token> validTokenList = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validTokenList.isEmpty()) {
            validTokenList.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validTokenList);
        }
    }


    public AuthenticationResponse register(RegisterRequest registerRequest) throws NotFoundException {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .fullname(registerRequest.getFullname())
                .enable(registerRequest.isEnable())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(
                        roleRepository.findByCode(registerRequest.getRoleCode()).orElseThrow(
                                () -> new NotFoundException(
                                        messageConfig.ERROR_ROLE_NOTFOUND+registerRequest.getRoleCode()
                                )
                        )
                )
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authRequest)
            throws UsernameOrPasswordInvalidException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        }
        catch (AuthenticationException exception) {
            throw new UsernameOrPasswordInvalidException(messageConfig.ERROR_USERNAME_PASSWORD_INVALID);
        }

        User user = getUser(authRequest.getUsername());

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllOldUserToken(user);
        saveToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws TokenIsInvalidException, IOException {
        final String authHeader = request.getHeader(constant.HTTP_HEADER_AUTHORIZATION);
        if (authHeader == null ||!authHeader.startsWith(constant.HTTP_HEADER_AUTHORIZATION_BEARER))
            return new AuthenticationResponse(null, null);

        final String refreshToken =authHeader.substring(constant.HTTP_HEADER_AUTHORIZATION_BEARER.length());
        final String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            User user = getUser(username);
            if(jwtService.isValidToken(refreshToken, user)) {
                String newAccessToken = jwtService.generateToken(user);
                revokeAllOldUserToken(user);
                saveToken(user, newAccessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                                                        .accessToken(newAccessToken)
                                                        .refreshToken(refreshToken)
                                                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                return authResponse;
            }
            else throw new TokenIsInvalidException(messageConfig.ERROR_JWT_INVALID_TOKEN);
        }
        return new AuthenticationResponse(null, null);
    }


    public UserDTO getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(constant.HTTP_HEADER_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(constant.HTTP_HEADER_AUTHORIZATION_BEARER))
            throw new TokenIsInvalidException(messageConfig.ERROR_JWT_INVALID_TOKEN);

        String jwt = authHeader.substring(constant.HTTP_HEADER_AUTHORIZATION_BEARER.length());
        String username = jwtService.extractUsername(jwt);
        User user = (User) userDetailsService.loadUserByUsername(username);

        return UserDTO.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .build();
    }


    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameOrPasswordInvalidException(messageConfig.ERROR_USERNAME_PASSWORD_INVALID)
        );
    }

}
