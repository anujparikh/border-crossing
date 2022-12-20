package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.exception.ExceptionHandler;
import org.example.persistence.repository.CrossingRepository;
import org.example.transformation.CrossingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Properties;

@SpringBootApplication
@ComponentScan("org.example")
public class Main implements CommandLineRunner {

  private static final String FORMAT = "csv";
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  @Autowired
  private ExceptionHandler exceptionHandler;

  @Autowired
  private SparkSession sparkSession;

  @Autowired
  @Qualifier("getMySqlProps")
  private Properties mysqlProps;

  @Autowired
  CrossingData crossingData;

  public static void main(String[] args) {
    LOGGER.info("Starting Batch Application");
    SpringApplication.run(Main.class, args);
    LOGGER.info("Batch Application Finished");
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      // Read a CSV file with the header, and store it in a DataFrame
      Dataset<Row> df = sparkSession.read().format(FORMAT).option("header", "true")
          .load("src/main/resources/spark-data/Border_Crossing_Entry_Data.csv");
      crossingData.saveCrossingData(df);
    } catch (Exception ex) {
      exceptionHandler.handleException(ex);
      throw ex;
    }
  }
}