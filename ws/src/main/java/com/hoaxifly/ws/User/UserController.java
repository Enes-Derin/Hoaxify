package com.hoaxifly.ws.User;

import com.hoaxifly.ws.Error.ApiError;
import com.hoaxifly.ws.Shared.GenericMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@Valid @RequestBody User user) {
        this.userService.createUser(user);
        return new GenericMessage("User created successfully");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerMethodNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setStatus(400);
        apiError.setMessage(exception.getMessage());
        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError:exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
    }
}
