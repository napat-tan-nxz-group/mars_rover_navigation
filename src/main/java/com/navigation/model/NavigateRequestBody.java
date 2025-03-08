package com.navigation.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Data
public class NavigateRequestBody {
  private Integer gridSize;
  private String[] obstacles;
  private String commands;

  public static Node convertTupleToNode(String obstacleLocation) {
    String trimmed = obstacleLocation.replace(")", "").replace("(", "");
    Integer horizontalLocation = Integer.valueOf(trimmed.split(",")[0]);
    Integer verticalLocation = Integer.valueOf(trimmed.split(",")[1]);
    return Node.builder()
        .verticalPosition(verticalLocation)
        .horizontalPosition(horizontalLocation)
        .build();
  }
}
