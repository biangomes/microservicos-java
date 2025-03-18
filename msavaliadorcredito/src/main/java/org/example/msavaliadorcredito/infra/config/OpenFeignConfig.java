package org.example.msavaliadorcredito.infra.config;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class OpenFeignConfig {

    @Bean
    public Feign.Builder feignBuilder(Encoder encoder, Decoder decoder) {
        return Feign.builder()
                .encoder(encoder)
                .decoder(new OptionalDecoder(decoder));
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverter) {
        return new SpringEncoder(messageConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverter) {
        return new SpringDecoder(messageConverter);
    }
}
