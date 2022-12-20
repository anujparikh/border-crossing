package org.example.mapper;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.example.persistence.model.Crossing;

public class CrossingMapper implements Function<Row, Crossing> {

  @Override
  public Crossing call(Row row) {
    int portNameColId = 0;
    int stateColId = 1;
    int portCodeColId = 2;
    int borderColId = 3;
    int dateColId = 4;
    int measureColId = 5;
    int valueColId = 6;
    int latitudeColId = 7;
    int longitudeColId = 8;
    int pointColId = 9;

    return new Crossing(
        Integer.parseInt(row.getString(portCodeColId)),
        row.getString(dateColId),
        row.getString(portNameColId),
        row.getString(stateColId),
        row.getString(borderColId),
        row.getString(measureColId),
        row.getString(valueColId),
        row.getString(latitudeColId),
        row.getString(longitudeColId),
        row.getString(pointColId)
    );
  }
}
