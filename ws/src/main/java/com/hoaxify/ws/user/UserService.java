package com.hoaxify.ws.user;

import com.hoaxify.ws.email.EmailService;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import com.hoaxify.ws.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackOn = MailException.class)
    public void createUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user. setActivationToken(UUID.randomUUID().toString());
            this.userRepository.save(user);
            emailService.sendActivationMail(user.getEmail(), user.getActivationToken());
        }catch (DataIntegrityViolationException ex) {
            throw new NotUniqueEmailException();
        }catch (MailException ex) {
            throw new ActivationNotificationException();
        }

    }

    public void activateUser(String token) {
        User inDb = this.userRepository.findByActivationToken(token);
        if (inDb == null) {
            throw new InvalidTokenException();
        }else {
            inDb.setActive(true);
            inDb.setActivationToken(null);
            this.userRepository.save(inDb);
        }
    }

    public Page<User> getAllUsers(Pageable page) {
        return this.userRepository.findAll(page);
    }

    public User getUser(int id) {
        Optional<User> indb =this.userRepository.findById(id);
        if (indb.isPresent()) {
            return indb.get();
        }else {
            throw new UserNotFoundException(id);
        }
    }
}
