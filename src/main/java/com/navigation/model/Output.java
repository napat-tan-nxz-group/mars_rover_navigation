package com.navigation.model;

import com.navigation.constant.Direction;
import com.navigation.constant.Status;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Output {
  private Node finalPosition;
  private Direction finalDirection;
  private Status finalStatus;
}
