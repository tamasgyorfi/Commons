package hu.bets.common.config.model;

import hu.bets.common.util.SchemaValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public SchemaValidator schemaValidator() {
        return new SchemaValidator();
    }
}
