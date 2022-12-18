package org.example.persistence.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "port")
public class Port {

  @Id
  private Integer code;
  private String name;
  private String state;
  private String border;

  @Override
  public String toString() {
    return String.format("Code: %d, Name: %s, State: %s, Border: %s", code, name, state, border);
  }
}
