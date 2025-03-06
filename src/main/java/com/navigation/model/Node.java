package com.navigation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Node {
  private Integer verticalPosition;
  private Integer horizontalPosition;
}
