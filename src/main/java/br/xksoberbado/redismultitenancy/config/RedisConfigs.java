package br.xksoberbado.redismultitenancy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class RedisConfigs {

    private final RedisProperties redisProperties;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        final var factories = new HashMap<String, LettuceConnectionFactory>();

        List.of(Map.entry("one", 6379), Map.entry("two", 6380))
            .forEach(entry -> {
                final var redisConfig = new RedisStandaloneConfiguration(redisProperties.getHost(), entry.getValue());

                final var factory = new LettuceConnectionFactory(redisConfig);
                factory.afterPropertiesSet();

                factories.put(entry.getKey(), factory);
            });

        final var defaultFactory = factories.values().iterator().next();

        return new TenantAwareRedisConnectionFactory(factories, defaultFactory);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory factory) {
        final var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

    @RequiredArgsConstructor
    private static final class TenantAwareRedisConnectionFactory implements RedisConnectionFactory {

        private final Map<String, LettuceConnectionFactory> tenantFactories;
        private final LettuceConnectionFactory defaultFactory;

        @Override
        public boolean getConvertPipelineAndTxResults() {
            return currentFactory().getConvertPipelineAndTxResults();
        }

        @Override
        public RedisConnection getConnection() {
            return currentFactory().getConnection();
        }

        @Override
        public RedisClusterConnection getClusterConnection() {
            return currentFactory().getClusterConnection();
        }

        @Override
        public RedisSentinelConnection getSentinelConnection() {
            return currentFactory().getSentinelConnection();
        }

        @Override
        public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
            return currentFactory().translateExceptionIfPossible(ex);
        }

        private LettuceConnectionFactory currentFactory() {
            return Optional.ofNullable(TenantHolder.get())
                .map(tenantFactories::get)
                .orElse(defaultFactory);
        }
    }
}
