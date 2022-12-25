package org.example.mapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.example.persistence.model.Crossing;
import org.example.persistence.model.Measure;
import org.example.persistence.model.Port;
import org.example.persistence.repository.MeasureRepository;
import org.example.persistence.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CrossingMapper implements Function<Row, Crossing> {

  @NonNull
  private Map<String, Measure> measureMapping;
  @NonNull
  private Map<Integer, Port> portMapping;

  @Override
  public Crossing call(Row row) {
    int portCodeColId = 2;
    int dateColId = 4;
    int measureColId = 5;
    int valueColId = 6;
    int pointColId = 9;

    return new Crossing(
        portMapping.get(Integer.parseInt(row.getString(portCodeColId))),
        row.getString(dateColId),
        measureMapping.get(row.getString(measureColId)),
        row.getString(valueColId),
        row.getString(pointColId)
    );
  }
}
