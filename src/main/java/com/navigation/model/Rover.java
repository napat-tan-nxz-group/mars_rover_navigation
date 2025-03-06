package com.navigation.model;

import com.navigation.constant.Command;
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

  public void moveForward(Direction newDirection) {
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
    log.info("Rover is nows at: {}, {}", this.getVerticalPosition(), this.getHorizontalPosition());
  }

  public Direction getRightDirection(Direction currentDirection) {
    if (currentDirection.equals(Direction.NORTH)) return Direction.EAST;
    if (currentDirection.equals(Direction.EAST)) return Direction.SOUTH;
    if (currentDirection.equals(Direction.SOUTH)) return Direction.WEST;
    //    final RIGHT
    else return Direction.NORTH;
  }

  public Direction getLeftDirection(Direction currentDirection) {
    if (currentDirection.equals(Direction.NORTH)) return Direction.WEST;
    if (currentDirection.equals(Direction.EAST)) return Direction.NORTH;
    if (currentDirection.equals(Direction.SOUTH)) return Direction.EAST;
    //    finally LEFT
    else return Direction.SOUTH;
  }

  public void executeCommand(Command command) {
    if (command.equals(Command.MOVE)) {
      log.info("Moving Forward");
      moveForward(currentDirection); // move forward command
    }
    //    change direction command
    if (command.equals(Command.LEFT)) {
      Direction newDirection = getLeftDirection(currentDirection);
      log.info("Turning left to Direction: {}", newDirection);
      this.setCurrentDirection(newDirection);
    }
    if (command.equals(Command.RIGHT)) {
      Direction newDirection = getRightDirection(currentDirection);
      log.info("Turning right to Direction: {}", newDirection);
      this.setCurrentDirection(newDirection);
    }
  }
}
