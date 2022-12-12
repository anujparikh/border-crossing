package org.example.persistence;

import org.springframework.beans.factory.annotation.Autowired;
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

//    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//    dataSourceBuilder.url(dbManager.getDbUrl());
//    dataSourceBuilder.username(dbManager.getUsername());
//    dataSourceBuilder.password(dbManager.getPassword());
//    return dataSourceBuilder.build();

    return DataSourceBuilder.create().build();
  }
}
