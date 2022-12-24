package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.exception.ExceptionHandler;
import org.example.persistence.model.Measure;
import org.example.persistence.model.Port;
import org.example.persistence.repository.CrossingRepository;
import org.example.persistence.repository.MeasureRepository;
import org.example.persistence.repository.PortRepository;
import org.example.transformation.CrossingData;
import org.example.transformation.MeasureTransformation;
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
  private MeasureTransformation measureTransformation;

  @Autowired
  private CrossingRepository crossingRepository;

  @Autowired
  private PortRepository portRepository;

  @Autowired
  private MeasureRepository measureRepository;

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
      df.printSchema();

      // TODO: proper way to group by
      // Getting all the unique ports from crossing data
      Dataset<Row> portsDf = df.groupBy(
          df.col("Port Code"),
          df.col("Port Name"),
          df.col("State"),
          df.col("Border"),
          df.col("Longitude"),
          df.col("Latitude")
      ).agg(df.col("Port Code"),
          df.col("Port Name"),
          df.col("State"),
          df.col("Border"),
          df.col("Longitude"),
          df.col("Latitude")
      ).select("Port Code",
          "Port Name",
          "State",
          "Border",
          "Longitude",
          "Latitude");
      portsDf.printSchema();
      List<Port> ports = portTransformation.portsDfToList(portsDf);
      portRepository.saveAll(ports);

      Dataset<Row> measuresDf = df.groupBy(
          df.col("Measure")
      ).agg(df.col("Measure")
      ).select("Measure");
      measuresDf.printSchema();
      List<Measure> measures = measureTransformation.measuresDfToList(measuresDf);
      measureRepository.saveAll(measures);

    } catch (Exception ex) {
      exceptionHandler.handleException(ex);
      throw ex;
    }
  }
}