package tienthuan.jwtauthenbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationRequest;
import tienthuan.jwtauthenbackend.dto.auth.AuthenticationResponse;
import tienthuan.jwtauthenbackend.dto.auth.RegisterRequest;
import tienthuan.jwtauthenbackend.exception.def.NotFoundException;
import tienthuan.jwtauthenbackend.exception.def.TokenIsInvalidException;
import tienthuan.jwtauthenbackend.exception.def.UsernameOrPasswordInvalidException;
import tienthuan.jwtauthenbackend.service.common.AuthenticationService;
import tienthuan.jwtauthenbackend.service.common.LogoutService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws NotFoundException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
            throws UsernameOrPasswordInvalidException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request,HttpServletResponse response)
            throws UsernameOrPasswordInvalidException, TokenIsInvalidException, IOException {
        return ResponseEntity.ok(authenticationService.refreshToken(request, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return ResponseEntity.ok().build();
    }

}
