package com.hoaxifly.ws.User;

import com.hoaxifly.ws.User.Exception.NotUniqueEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    void createUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new NotUniqueEmailException();
        }

    }
}
