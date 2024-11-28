package com.hoaxify.ws.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "hoaxify")
@Configuration
@Data
public class HoaxifyProperties {

    private Email email;
    private Client client;

    public static record Email(
      String username,
      String password,
      String host,
      int port,
      String from
    ){ }

    public static record Client(
            String host
    ){}

}
