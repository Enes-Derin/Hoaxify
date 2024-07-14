package com.hoaxifly.ws.User.Exception;

import com.hoaxifly.ws.Shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;


// eşsiz mail hatası için
public class NotUniqueEmailException extends RuntimeException {

    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("hoaxify.constraint.email.uniqueEmail", LocaleContextHolder.getLocale()));
    }

    public Map<String,String> getValidationErrors(){
        return Collections.singletonMap("email", Messages.getMessageForLocale("hoaxify.error.validation",
                LocaleContextHolder.getLocale()));
    }
}
