package io.github.zapolyarnydev.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
@Getter
@Setter
public class BotProperties {
    private String username;
    private String token;
}
