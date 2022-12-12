package org.example.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "port")
public class Port {

  private Integer code;
  private String name;
  private String state;
  private String border;

  @Override
  public String toString() {
    return String.format("Code: %d, Name: %s, State: %s, Border: %s", code, name, state, border);
  }
}
