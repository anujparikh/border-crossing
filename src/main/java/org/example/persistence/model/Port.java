package org.example.persistence.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "port")
@NoArgsConstructor
@RequiredArgsConstructor
public class Port implements Serializable {

  @Id
  @NonNull
  private Integer code;
  @NonNull
  private String name;
  @NonNull
  private String state;
  @NonNull
  private String border;
  @NonNull
  private Double longitude;
  @NonNull
  private Double latitude;

  @Override
  public String toString() {
    return String.format("Code: %d, Name: %s, State: %s, Border: %s", code, name, state, border);
  }
}
