package org.example.transformation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.mapper.CrossingMapper;
import org.example.persistence.model.Crossing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrossingData {

  @Autowired
  SparkSession sparkSession;

  public List<Crossing> crossingsDfToList(Dataset<Row> df) throws Exception {
    CrossingMapper crossingMapper = new CrossingMapper();
    JavaRDD<Row> rdd = df.toDF().toJavaRDD();
    JavaRDD<Crossing> crossingJavaRDD = rdd.map(crossingMapper);
    return crossingJavaRDD.collect();
  }

  public Dataset<Crossing> crossingListToDF(List<Crossing> crossings) throws Exception {
    return sparkSession.createDataset(crossings, Encoders.bean(Crossing.class));
  }
}
