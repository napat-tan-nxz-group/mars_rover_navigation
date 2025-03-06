package com.navigation.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Grid {
  private Integer verticalSize;
  private Integer horizontalSize;
  private List<Node> obstacles;
}
