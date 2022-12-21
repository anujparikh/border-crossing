package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.exception.ExceptionHandler;
import org.example.persistence.model.Crossing;
import org.example.persistence.model.Port;
import org.example.persistence.repository.CrossingRepository;
import org.example.persistence.repository.PortRepository;
import org.example.transformation.CrossingData;
import org.example.transformation.PortTransformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
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
  private CrossingData crossingData;

  @Autowired
  private PortTransformation portTransformation;

  @Autowired
  private CrossingRepository crossingRepository;

  @Autowired
  private PortRepository portRepository;

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

      // Saving all the crossing data to "crossing" table
      List<Crossing> crossings = crossingData.crossingsDfToList(df);
      // crossingRepository.saveAll(crossings);

      // TODO: proper way to group by
      // Getting all the unique ports from crossing data
      Dataset<Crossing> crossingDf = crossingData.crossingListToDF(crossings);
      crossingDf.printSchema();
      Dataset<Row> portsDf = crossingDf.groupBy(
          crossingDf.col("portcode"),
          crossingDf.col("portname"),
          crossingDf.col("state"),
          crossingDf.col("border")
      ).agg(crossingDf.col("portcode"),
          crossingDf.col("portname"),
          crossingDf.col("state"),
          crossingDf.col("border")
      ).select("portcode",
          "portname",
          "state",
          "border");
      portsDf.printSchema();
      List<Port> ports = portTransformation.portsDfToList(portsDf);
      portRepository.saveAll(ports);

    } catch (Exception ex) {
      exceptionHandler.handleException(ex);
      throw ex;
    }
  }
}