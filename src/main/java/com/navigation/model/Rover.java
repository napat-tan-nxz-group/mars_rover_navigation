package com.navigation.model;

import com.navigation.constant.Direction;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Rover extends Node {
  private Direction currentDirection;
  private Integer roverSpeed;

  public void moveForward(Grid grid, Direction newDirection) {
    this.setCurrentDirection(newDirection);
    if (newDirection.equals(Direction.NORTH)) {
      this.setVerticalPosition(this.getVerticalPosition() + this.roverSpeed);
    }
    if (newDirection.equals(Direction.EAST)) {
      this.setHorizontalPosition(this.getHorizontalPosition() + this.roverSpeed);
    }
    if (newDirection.equals(Direction.SOUTH)) {
      this.setVerticalPosition(this.getVerticalPosition() - this.roverSpeed);
    }
    if (newDirection.equals(Direction.WEST)) {
      this.setHorizontalPosition(this.getHorizontalPosition() - this.roverSpeed);
    }
    log.info("Rover is not at: {}, {}", this.getVerticalPosition(), this.getHorizontalPosition());

    // TODO: case for outbound and obstacle
  }
}
