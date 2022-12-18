package org.example.persistence.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "crossing")
public class Crossing implements Serializable {

  private String portname;
  private String state;
  @Id
  private Integer portcode;
  private String border;
  @Id
  private String date;
  private String measure;
  private Integer value;
  private Double longitude;
  private Double latitude;
  private String point;

}
