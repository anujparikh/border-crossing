package org.example.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "crossing")
public class Crossing {

  private String portname;
  private String state;
  private Integer portcode;
  private String border;
  private String date;
  private String measure;
  private Integer value;
  private Double longitude;
  private Double latitude;
  private String point;

}
