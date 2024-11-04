package dev.andreasarf.loan.config;

import io.micrometer.common.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.password}")
    private String password;

//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig server = config.useSingleServer();
        server.setAddress(host);

        if (StringUtils.isNotEmpty(password)) {
            server.setPassword(password);
        }
        return Redisson.create(config);
    }
}
