package org.example.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

  public static final String TABLE_NAME = "crossing";
  public static final String DB_URL = "jdbc:mysql://localhost/sys?user=root&password=123456";
  public static final String EMBEDDED_DRIVER_STRING = "com.mysql.jdbc.Driver";

  public void startDB() {
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
    } catch (SQLException ex) {
      System.out.println("SQL Exception: " + ex.getMessage());
      System.out.println("SQL State: " + ex.getSQLState());
      System.out.println("Vendor Error: " + ex.getErrorCode());
    }
  }

  public Properties buildDBProperties() {
    Properties props = new Properties();
    props.setProperty("driver", EMBEDDED_DRIVER_STRING);
    props.setProperty("user", "root");
    props.setProperty("password", "123456");
    return props;
  }

  public void displayTableResults(int numOfRecords) throws Exception {
    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      int printed = 0;
      conn = DriverManager.getConnection(DB_URL);
      statement = conn.prepareStatement("SELECT * FROM " + TABLE_NAME);
      resultSet = statement.executeQuery();
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int columnsNumber = rsmd.getColumnCount();

      while (resultSet.next() && printed < numOfRecords) {
        LOGGER.info("======= RECORD: " + printed + " ===========");
        for (int i = 1; i <= columnsNumber; i++) {
          if (i > 1) {
            System.out.print(",  ");
          }
          String columnValue = resultSet.getString(i);
          LOGGER.info("COLUMN: " + rsmd.getColumnName(i) + " - VALUE:  " + columnValue);
        }
        LOGGER.info("================================");
        printed++;
      }
    } catch (Exception e) {
      throw e;
    } finally {
      close(conn, statement, resultSet);
    }
  }

  private void close(Connection conn, PreparedStatement statement, ResultSet resultSet)
      throws Exception {
    if (resultSet != null) {
      resultSet.close();
    }
    if (statement != null) {
      statement.close();
    }
    if (conn != null) {
      conn.close();
    }
  }
}
