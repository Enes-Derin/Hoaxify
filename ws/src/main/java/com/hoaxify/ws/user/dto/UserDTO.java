package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import lombok.Data;

@Data
public class UserDTO {
    long id;
    String username;
    String email;
    String image;

    public UserDTO(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setImage(user.getImage());
    }
}
