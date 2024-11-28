package com.hoaxify.ws.user;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody UserCreate userCreate) {
    this.userService.createUser(userCreate.toUser());
    String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
    return new GenericMessage(message);
    }

    @PatchMapping("/api/v1/users/{token}/activate")
    public GenericMessage activateUser(@PathVariable String token) {
        this.userService.activateUser(token);
        var message = Messages.getMessageForLocale("hoaxify.activate.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("/api/v1/users")
    public Page<User> getAllUsers(Pageable page) {
        return this.userService.getAllUsers(page);
    }

    // for validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgNotValidException(MethodArgumentNotValidException exception){
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("hoaxify.error.validation.message", LocaleContextHolder.getLocale());
        error.setMessage(message);
        error.setStatus(400);
        Map<String,String> validationErrors = new HashMap<>();
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()){
           validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    public ResponseEntity<ApiError> handleNotUniqueEmailException(NotUniqueEmailException exception){
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        error.setMessage(exception.getMessage());
        error.setStatus(400);
        error.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    public ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception){
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        error.setMessage(exception.getMessage());
        error.setStatus(502);
        return ResponseEntity.status(502).body(error);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception){
        ApiError error = new ApiError();
        error.setPath("/api/v1/users");
        error.setMessage(exception.getMessage());
        error.setStatus(400);
        return ResponseEntity.status(400).body(error);
    }
}
