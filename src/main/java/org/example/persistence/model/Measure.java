package org.example.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "measure")
@NoArgsConstructor
@RequiredArgsConstructor
public class Measure implements Serializable {

  @javax.persistence.Id
  @GeneratedValue
  private Long Id;
  @NonNull
  private String type;

}
