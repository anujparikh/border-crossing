package org.example;

import static org.example.persistence.DbManager.DB_URL;
import static org.example.persistence.DbManager.TABLE_NAME;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.example.persistence.DbManager;

import java.util.Properties;

public class Main {

  private static final String APP_NAME = "BroderCrossing";
  private static final String LOCAL_NODE_ID = "local";
  private static final String FORMAT = "csv";
  public static void main(String[] args) throws Exception {
    Main main = new Main();
    main.init();
  }

  private static void init() throws Exception {
    // Create the Spark session on the localhost master node
    SparkSession sparkSession = SparkSession.builder()
        .appName(APP_NAME)
        .master(LOCAL_NODE_ID)
        .getOrCreate();

    // Read a CSV file with the header, and store it in a DataFrame
    Dataset<Row> df = sparkSession.read().format(FORMAT)
        .option("header", "true")
        .load("src/main/resources/spark-data/Border_Crossing_Entry_Data.csv");

    // Start DB connection
    DbManager dbManager = new DbManager();
    dbManager.startDB();

    Properties prop = dbManager.buildDBProperties();
    df.write().mode(SaveMode.Overwrite)
            .jdbc(DB_URL, TABLE_NAME, prop);

    dbManager.displayTableResults(5);

    df.show(15);
  }
}