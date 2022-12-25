package org.example.transformation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.mapper.CrossingMapper;
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
public class CrossingTransformation {

  @Autowired
  SparkSession sparkSession;
  @Autowired
  private MeasureRepository measureRepository;
  @Autowired
  private PortRepository portRepository;

  private Map<String, Measure> getMeasureMappings() {
    List<Measure> measures = measureRepository.findAll();
    Map<String, Measure> measureMapping = new HashMap<>();
    measures.forEach(measure -> {
      measureMapping.put(measure.getType(), measure);
    });
    return measureMapping;
  }

  private Map<Integer, Port> getPortMappings() {
    List<Port> ports = portRepository.findAll();
    Map<Integer, Port> portMapping = new HashMap<>();
    ports.forEach(port -> {
      portMapping.put(port.getCode(), port);
    });
    return portMapping;
  }

  public List<Crossing> crossingsDfToList(Dataset<Row> df) throws Exception {
    CrossingMapper crossingMapper = new CrossingMapper(getMeasureMappings(), getPortMappings());
    JavaRDD<Row> rdd = df.toDF().toJavaRDD();
    JavaRDD<Crossing> crossingJavaRDD = rdd.map(crossingMapper);
    return crossingJavaRDD.collect();
  }

  public Dataset<Crossing> crossingListToDF(List<Crossing> crossings) throws Exception {
    return sparkSession.createDataset(crossings, Encoders.bean(Crossing.class));
  }
}
