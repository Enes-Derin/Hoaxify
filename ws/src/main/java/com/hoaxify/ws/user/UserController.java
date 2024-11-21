package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    public GenericMessage createUser(@RequestBody User user) {
    this.userService.createUser(user);
    return new GenericMessage("User created");
    }

}
