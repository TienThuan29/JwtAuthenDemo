package tienthuan.jwtauthenbackend.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tienthuan.jwtauthenbackend.config.MessageConfiguration;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationRequest;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationResponse;
import tienthuan.jwtauthenbackend.dto.auth.RegisterRequest;
import tienthuan.jwtauthenbackend.dto.info.UserDTO;
import tienthuan.jwtauthenbackend.exception.def.NotFoundException;
import tienthuan.jwtauthenbackend.exception.def.UsernameOrPasswordInvalidException;
import tienthuan.jwtauthenbackend.model.User;
import tienthuan.jwtauthenbackend.repository.RoleRepository;
import tienthuan.jwtauthenbackend.repository.UserRepository;
import tienthuan.jwtauthenbackend.service.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final MessageConfiguration messageConfig;


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

        return AuthenticationResponse.builder()
                .token(jwtToken)
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

        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> new UsernameOrPasswordInvalidException(messageConfig.ERROR_USERNAME_PASSWORD_INVALID)
        );
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserDTO getUserInfo(String token) {
        String username = jwtService.extractUsername(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        return UserDTO.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .build();
    }

}
