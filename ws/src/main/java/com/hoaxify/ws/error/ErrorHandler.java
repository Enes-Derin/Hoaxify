package com.hoaxify.ws.error;

import com.hoaxify.ws.auth.exception.AuthenticationException;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import com.hoaxify.ws.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    // for validation exception
    @ExceptionHandler(
            {MethodArgumentNotValidException.class,
                    NotUniqueEmailException.class,
                    ActivationNotificationException.class,
                    InvalidTokenException.class,
                    UserNotFoundException.class,
                    AuthenticationException.class
            }
    )
    public ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError error = new ApiError();
        error.setPath(request.getRequestURI());
        error.setMessage(exception.getMessage());
        if (exception instanceof MethodArgumentNotValidException) {
            String message = Messages.getMessageForLocale("hoaxify.error.validation.message", LocaleContextHolder.getLocale());
            error.setMessage(message);
            error.setStatus(400);
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            error.setValidationErrors(validationErrors);
            return ResponseEntity.status(400).body(error);
        }
        else if (exception instanceof NotUniqueEmailException) {
            error.setStatus(400);
            error.setValidationErrors(((NotUniqueEmailException) exception).getValidationErrors());
        }
        else if (exception instanceof ActivationNotificationException) {
            error.setStatus(502);
        }
        else if (exception instanceof InvalidTokenException) {
            error.setStatus(400);
        }
        else if (exception instanceof UserNotFoundException) {
            error.setStatus(404);
        }
        else if (exception instanceof AuthenticationException) {
            error.setStatus(401);
        }
        return ResponseEntity.status(error.getStatus()).body(error);
    }


    // kullanılan mail hatası
    /*@ExceptionHandler(NotUniqueEmailException.class)
    public ResponseEntity<ApiError> handleNotUniqueEmailException(NotUniqueEmailException exception) {
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        error.setMessage(exception.getMessage());
        error.setStatus(400);
        error.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(error);
    }*/

    // aktivasyon kodu geçersiz olunca gösterilen hata
    /*@ExceptionHandler(ActivationNotificationException.class)
    public ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception) {
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        error.setMessage(exception.getMessage());
        error.setStatus(502);
        return ResponseEntity.status(502).body(error);
    }
    */



    // Token bulunamadı hatası
   /* @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception, HttpServletRequest request) {
        ApiError error = new ApiError();
        error.setPath(request.getRequestURI());
        error.setMessage(exception.getMessage());

        return ResponseEntity.status(400).body(error);
    }*/

    // kullanıcı bulunamadı hatası
    /*@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ApiError error = new ApiError();
        error.setPath(request.getRequestURI());
        error.setMessage(exception.getMessage());
        error.setStatus(404);
        return ResponseEntity.status(404).body(error);

    }*/


    // doğrulama hatası
    /*@ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError error = new ApiError();
        error.setPath("/api/v1/auth");
        error.setMessage(ex.getMessage());
        error.setStatus(401);
        return ResponseEntity.status(401).body(error);
    }*/
}
