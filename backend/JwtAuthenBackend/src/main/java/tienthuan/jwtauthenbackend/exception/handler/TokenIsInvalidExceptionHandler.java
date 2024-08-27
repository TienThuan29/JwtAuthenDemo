package tienthuan.jwtauthenbackend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tienthuan.jwtauthenbackend.exception.def.ErrorResponse;
import tienthuan.jwtauthenbackend.exception.def.TokenIsInvalidException;

@RestControllerAdvice
public class TokenIsInvalidExceptionHandler {

    @ExceptionHandler(TokenIsInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handle(TokenIsInvalidException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

}
