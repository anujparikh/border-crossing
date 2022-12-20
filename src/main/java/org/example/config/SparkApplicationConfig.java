package org.example.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class SparkApplicationConfig {

  @Autowired
  private Environment env;

  @Value("${spark.app.name}")
  private String appName;

  @Value("${spark.app.node}")
  private String appNode;

  @Value("${spring.datasource.jdbc-url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String driver;

  @Bean
  public SparkConf sparkConf() {
    SparkConf sparkConf = new SparkConf()
        .setAppName(appName)
        .setMaster(appNode);

    return sparkConf;
  }

  @Bean
  public JavaSparkContext javaSparkContext() {
    return new JavaSparkContext(sparkConf());
  }

  @Bean
  public SparkSession sparkSession() {
    return SparkSession
        .builder()
        .sparkContext(javaSparkContext().sc())
        .appName(appName)
        .getOrCreate();
  }

  @Bean
  public Properties getMySqlProps() {
    Properties props = new Properties();
    props.setProperty("dbUrl", dbUrl);
    props.setProperty("user", username);
    props.setProperty("password", password);
    props.setProperty("driver", driver);
    return props;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
