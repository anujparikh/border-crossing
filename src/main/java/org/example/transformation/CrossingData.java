package org.example.transformation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.example.mapper.CrossingMapper;
import org.example.persistence.model.Crossing;
import org.example.persistence.repository.CrossingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrossingData {

  @Autowired
  private CrossingRepository crossingRepository;

  public void saveCrossingData(Dataset<Row> df) throws Exception {
    CrossingMapper crossingMapper = new CrossingMapper();
    JavaRDD<Row> rdd = df.toDF().toJavaRDD();
    JavaRDD<Crossing> crossingJavaRDD = rdd.map(crossingMapper);
    List<Crossing> crossings = crossingJavaRDD.collect();
    System.out.println(crossings.size());
    crossingRepository.saveAll(crossings);
  }
}
