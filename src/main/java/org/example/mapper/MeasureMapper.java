package org.example.mapper;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.example.persistence.model.Measure;
import org.example.persistence.model.Port;

public class MeasureMapper implements Function<Row, Measure> {
  @Override
  public Measure call(Row row) {
    int measureTypeColId = 0;

    return new Measure(
        row.getString(measureTypeColId)
    );
  }
}
