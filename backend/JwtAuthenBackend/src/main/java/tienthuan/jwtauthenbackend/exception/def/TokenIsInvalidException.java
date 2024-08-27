package tienthuan.jwtauthenbackend.exception.def;

public class TokenIsInvalidException extends RuntimeException {
    public TokenIsInvalidException(String message) {
        super(message);
    }
}
