package test.ivan.vote.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.ivan.vote.common.util.SnowflakeIdFactory;

@Configuration
public class IdWorkerConfig {

    @Bean
    public SnowflakeIdFactory idFactory() {
        return new SnowflakeIdFactory(1, 1);
    }
}
