package com.hoaxifly.ws.User;

import com.hoaxifly.ws.Error.ApiError;
import com.hoaxifly.ws.User.Exception.NotUniqueEmailException;
import com.hoaxifly.ws.Shared.GenericMessage;
import com.hoaxifly.ws.Shared.Messages;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    UserService userService;
    //MessageSource messageSource;

    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody User user) {
        System.err.println(LocaleContextHolder.getLocale().getLanguage());
        this.userService.createUser(user);
        String message= Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerMethodNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setStatus(400);
        String message= Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError:exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    public ResponseEntity<ApiError> notUniqueEmailException(NotUniqueEmailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setStatus(400);
        apiError.setMessage(exception.getMessage());
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.badRequest().body(apiError);
    }
}
