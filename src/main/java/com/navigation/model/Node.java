package com.navigation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
  private Integer verticalPosition;
  private Integer horizontalPosition;

  public String getFinalLocation() {
    return "[%s, %s]".formatted(this.getHorizontalPosition(), this.getVerticalPosition());
  }
}
