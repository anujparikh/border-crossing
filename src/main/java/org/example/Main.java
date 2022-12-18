package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.example.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Properties;

@SpringBootApplication
public class Main implements CommandLineRunner {

  private static final String APP_NAME = "BroderCrossing";
  private static final String LOCAL_NODE_ID = "local";
  private static final String FORMAT = "csv";
  private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
  @Autowired
  private ExceptionHandler exceptionHandler;

  @Autowired
  private Environment env;

  public static void main(String[] args) {
    LOGGER.info("Starting Batch Application");
    SpringApplication.run(Main.class, args);
    LOGGER.info("Batch Application Finished");
  }

  @Override
  public void run(String... args) {
    try {
      // Create the Spark session on the localhost master node
      SparkSession sparkSession = SparkSession.builder().appName(APP_NAME).master(LOCAL_NODE_ID)
          .getOrCreate();

      // Read a CSV file with the header, and store it in a DataFrame
      Dataset<Row> df = sparkSession.read().format(FORMAT).option("header", "true")
          .load("src/main/resources/spark-data/Border_Crossing_Entry_Data.csv");

      String username = env.getProperty("spring.datasource.username");
      String dbUrl = env.getProperty("spring.datasource.jdbc-url");
      String password = env.getProperty("spring.datasource.password");
      String driver = env.getProperty("spring.datasource.driver-class-name");

      Properties props = new Properties();
      props.setProperty("user", username);
      props.setProperty("password", password);
      props.setProperty("driver", driver);

      df.write().mode(SaveMode.Overwrite).jdbc(dbUrl, "crossing", props);

      df.show(15);
    } catch (Exception ex) {
      exceptionHandler.handleException(ex);
      throw ex;
    }
  }
}