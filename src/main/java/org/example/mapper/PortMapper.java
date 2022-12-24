package org.example.mapper;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.example.persistence.model.Port;

public class PortMapper implements Function<Row, Port> {

  @Override
  public Port call(Row row) {
    int portCodeColId = 0;
    int portNameColId = 1;
    int stateColId = 2;
    int borderColId = 3;
    int longitudeColId = 4;
    int latitudeColId = 5;

    return new Port(
        Integer.parseInt(row.getString(portCodeColId)),
        row.getString(portNameColId),
        row.getString(stateColId),
        row.getString(borderColId),
        Double.parseDouble(row.getString(longitudeColId)),
        Double.parseDouble(row.getString(latitudeColId))
    );
  }
}
