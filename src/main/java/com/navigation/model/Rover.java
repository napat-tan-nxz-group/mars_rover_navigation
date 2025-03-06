package com.navigation.model;

import com.navigation.constant.Direction;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Rover extends Node {
  private Direction currentDirection;
}
