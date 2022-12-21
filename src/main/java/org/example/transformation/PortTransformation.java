package org.example.transformation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.mapper.PortMapper;
import org.example.persistence.model.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortTransformation {

  @Autowired
  SparkSession sparkSession;

  public List<Port> portsDfToList(Dataset<Row> df) throws Exception {
    PortMapper portMapper = new PortMapper();
    JavaRDD<Row> rdd = df.toDF().toJavaRDD();
    JavaRDD<Port> crossingJavaRDD = rdd.map(portMapper);
    return crossingJavaRDD.collect();
  }
}
