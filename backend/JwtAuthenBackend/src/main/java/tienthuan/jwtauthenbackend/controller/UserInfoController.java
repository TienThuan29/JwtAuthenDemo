package tienthuan.jwtauthenbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tienthuan.jwtauthenbackend.dto.info.UserDTO;
import tienthuan.jwtauthenbackend.service.common.AuthenticationService;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfoFromToken(@RequestParam String token) {
        UserDTO userDTO = authenticationService.getUserInfo(token.trim());
        return ResponseEntity.ok(userDTO);
    }
}
