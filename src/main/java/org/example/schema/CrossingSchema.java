package org.example.schema;

import lombok.AllArgsConstructor;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public enum CrossingSchema {
  PORT_NAME("portname", DataTypes.StringType, true, null),
  STATE("state", DataTypes.StringType, true, null),
  PORT_CODE("portcode", DataTypes.IntegerType, true, null),
  BORDER("border", DataTypes.StringType, true, null),
  DATE("date", DataTypes.StringType, true, null),
  MEASURE("measure", DataTypes.StringType, true, null),
  VALUE("value", DataTypes.IntegerType, true, null),
  LONGITUDE("longitude", DataTypes.DoubleType, true, null),
  LATITUDE("latitude", DataTypes.DoubleType, true, null),
  POINT("point", DataTypes.StringType, true, null);

  private String name;
  private DataType type;
  private boolean nullable;
  private Metadata metadata;

  CrossingSchema(String name, DataType stringType, boolean nullable, Metadata metadata) {
    this.name = name;
    this.type = stringType;
    this.nullable = nullable;
    this.metadata = metadata;
  }

  public static StructType getSparkSchema() {
    int schemaSize = values().length;
    StructField[] fields = new StructField[schemaSize];
    CrossingSchema[] schemaFields = values();

    for (int i = 0; i < schemaSize; i++) {
      fields[i] = new StructField(schemaFields[i].name, schemaFields[i].type, schemaFields[i].nullable, schemaFields[i].metadata);
    }
    return DataTypes.createStructType(fields);
  }
}
