package org.example.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "crossing")
@NoArgsConstructor
@RequiredArgsConstructor
public class Crossing implements Serializable {

  @Id
  @GeneratedValue
  private Long Id;
  @NonNull
  private Integer portcode;
  @NonNull
  private String date;
  @NonNull
  private String portname;

  @NonNull
  private String state;
  @NonNull
  private String border;
  @NonNull
  private String measure;
  @NonNull
  private String value;
  @NonNull
  private String longitude;
  @NonNull
  private String latitude;
  @NonNull
  private String point;
}
