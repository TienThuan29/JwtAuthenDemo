package tienthuan.jwtauthenbackend.dto.info;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tienthuan.jwtauthenbackend.model.Role;

@Builder
@Getter
@Setter
public class UserDTO {

    private String username;

    private String fullname;

}
