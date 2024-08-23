package tienthuan.jwtauthenbackend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tienthuan.jwtauthenbackend.exception.def.ErrorResponse;
import tienthuan.jwtauthenbackend.exception.def.UsernameOrPasswordInvalidException;

@RestControllerAdvice
public class UsernamePasswordInvalidExceptionHandler {

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handle(UsernameOrPasswordInvalidException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

}
