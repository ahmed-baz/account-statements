package com.demo.tree.handler;


import com.demo.tree.dto.AppResponse;
import com.demo.tree.exceptions.AccountNotFoundException;
import com.demo.tree.exceptions.InvalidCredentialsException;
import com.demo.tree.exceptions.LoginTwiceException;
import com.demo.tree.exceptions.UnauthorizedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse<Void> appResponse = AppResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        AppResponse<Void> appResponse = AppResponse.failed(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginTwiceException.class)
    public ResponseEntity<Object> handleLoginTwiceException(LoginTwiceException ex, WebRequest request) {
        AppResponse<Void> appResponse = AppResponse.failed(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UnauthorizedUserException.class, InvalidCredentialsException.class})
    public ResponseEntity<Object> handleSecurityException(Exception ex, WebRequest request) {
        AppResponse<Void> appResponse = AppResponse.failed(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = (ex != null && ex.getFieldError() != null) ? ex.getFieldError().getDefaultMessage() : null;
        AppResponse<Void> appResponse = AppResponse.failed(HttpStatus.BAD_REQUEST.value(), msg);
        return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
    }


}
