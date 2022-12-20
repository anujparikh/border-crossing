package org.example.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor(force = true)
public class CrossingId implements Serializable {

  @NonNull
  private Integer portcode;
  @NonNull
  private String date;

}
