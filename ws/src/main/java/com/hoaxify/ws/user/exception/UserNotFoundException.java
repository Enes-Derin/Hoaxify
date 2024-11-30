package com.hoaxify.ws.user.exception;

import com.hoaxify.ws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super(Messages.getMessageForLocale("hoaxify.user.not.found", LocaleContextHolder.getLocale() , id));
    }
}
