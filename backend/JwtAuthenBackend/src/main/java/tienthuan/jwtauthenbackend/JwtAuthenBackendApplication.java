package tienthuan.jwtauthenbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tienthuan.jwtauthenbackend.config.ApplicationConfiguration;
import tienthuan.jwtauthenbackend.config.ConstantConfiguration;
import tienthuan.jwtauthenbackend.config.JwtAuthenticationFilter;
import tienthuan.jwtauthenbackend.service.jwt.JwtService;

@SpringBootApplication
public class JwtAuthenBackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JwtAuthenBackendApplication.class, args);
    }

}
