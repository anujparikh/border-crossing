package org.example.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "code")
  private Port port;
  @NonNull
  private String date;
  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "measureId")
  private Measure measure;
  @NonNull
  private String value;
  @NonNull
  private String point;
}
