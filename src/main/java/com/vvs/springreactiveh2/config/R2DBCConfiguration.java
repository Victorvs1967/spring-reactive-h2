package com.vvs.springreactiveh2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;

public class R2DBCConfiguration extends AbstractR2dbcConfiguration {

  @Bean
  public H2ConnectionFactory connectionFactory() {
    return new H2ConnectionFactory(
      H2ConnectionConfiguration.builder()
        .url("r2dbc:h2:mem:default;DB_CLOSE_DELAY=-1;")
        .username("sa")
        .build()
    );
  }
  
}
