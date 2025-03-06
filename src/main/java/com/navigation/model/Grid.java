package com.navigation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Grid {
  private Integer verticalSize;
  private Integer horizontalSize;
}
