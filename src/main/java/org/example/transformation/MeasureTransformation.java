package org.example.transformation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.mapper.MeasureMapper;
import org.example.mapper.PortMapper;
import org.example.persistence.model.Measure;
import org.example.persistence.model.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeasureTransformation {
  @Autowired
  SparkSession sparkSession;
  public List<Measure> measuresDfToList(Dataset<Row> df) throws Exception {
    MeasureMapper measureMapper = new MeasureMapper();
    JavaRDD<Row> rdd = df.toDF().toJavaRDD();
    JavaRDD<Measure> measureJavaRDD = rdd.map(measureMapper);
    return measureJavaRDD.collect();
  }
}
