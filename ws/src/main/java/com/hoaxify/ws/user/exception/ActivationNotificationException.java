package com.hoaxify.ws.user.exception;

import com.hoaxify.ws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException{

    public ActivationNotificationException(){
        super(Messages.getMessageForLocale("hoaxify.create.user.mail.failure", LocaleContextHolder.getLocale()));
    }
}
