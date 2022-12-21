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

    return new Port(
        row.getInt(portCodeColId),
        row.getString(portNameColId),
        row.getString(stateColId),
        row.getString(borderColId)
    );
  }
}
