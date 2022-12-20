package org.example.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DBConfig {

  @Bean
  @ConfigurationProperties("spring.datasource")
  public DataSource getDataSource() {
    return DataSourceBuilder.create().build();
  }
}
