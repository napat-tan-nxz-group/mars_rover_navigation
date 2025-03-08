package com.navigation.model;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.constant.Status;
import com.navigation.exception.RoverException;
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

  public void executeCommand(Grid grid, Command command) throws RoverException {
    if (command.equals(Command.MOVE)) {
      log.info("Moving Forward");
      moveForward(grid, this.getCurrentDirection()); // move forward command
    }
    //    change direction command
    if (command.equals(Command.LEFT)) {
      Direction newDirection = getLeftDirection(this.getCurrentDirection());
      log.info("Turning left to Direction: {}", newDirection);
      this.setCurrentDirection(newDirection);
    }
    if (command.equals(Command.RIGHT)) {
      Direction newDirection = getRightDirection(this.getCurrentDirection());
      log.info("Turning right to Direction: {}", newDirection);
      this.setCurrentDirection(newDirection);
    }
  }

  public void moveForward(Grid grid, Direction newDirection) throws RoverException {
    Node newPosition = reposition(newDirection);
    //    out of grid case
    if (isOutOfBound(newPosition, grid)) {
      log.error("Rover Out of Bound");
      throw new RoverException(
          Output.builder()
              .finalDirection(this.getCurrentDirection().getSymbol())
              .finalPosition(this.getFinalLocation())
              .finalStatus(Status.OUTBOUND.getMessage())
              .build());
    }

    this.setVerticalPosition(newPosition.getVerticalPosition());
    this.setHorizontalPosition(newPosition.getHorizontalPosition());

    //    encounter obstacle
    if (encounterObstacle(grid)) {
      throw new RoverException(
          Output.builder()
              .finalDirection(this.getCurrentDirection().getSymbol())
              .finalPosition(this.getFinalLocation())
              .finalStatus(Status.BLOCKED.getMessage())
              .build());
    }
    log.info("Rover is now at: {}, {}", this.getHorizontalPosition(), this.getVerticalPosition());
  }

  public Node reposition(Direction newDirection) throws RoverException {
    if (newDirection.equals(Direction.NORTH)) {
      return Node.builder()
          .horizontalPosition(this.getHorizontalPosition())
          .verticalPosition(this.getVerticalPosition() + this.getRoverSpeed())
          .build();
    }
    if (newDirection.equals(Direction.EAST)) {
      return Node.builder()
          .horizontalPosition(this.getHorizontalPosition() + this.getRoverSpeed())
          .verticalPosition(this.getVerticalPosition())
          .build();
    }
    if (newDirection.equals(Direction.SOUTH)) {
      return Node.builder()
          .horizontalPosition(this.getHorizontalPosition())
          .verticalPosition(this.getVerticalPosition() - this.getRoverSpeed())
          .build();
    }
    if (newDirection.equals(Direction.WEST)) {
      return Node.builder()
          .horizontalPosition(this.getHorizontalPosition() - this.getRoverSpeed())
          .verticalPosition(this.getVerticalPosition())
          .build();
    } else {
      log.error("Invalid Reposition Direction");
      throw new RoverException(
          Output.builder()
              .finalDirection(this.getCurrentDirection().getSymbol())
              .finalPosition(this.getFinalLocation())
              .finalStatus(Status.INVALID_DIRECTION.getMessage())
              .build());
    }
  }

  public Direction getRightDirection(Direction currentDirection) throws RoverException {
    if (currentDirection.equals(Direction.NORTH)) return Direction.EAST;
    if (currentDirection.equals(Direction.EAST)) return Direction.SOUTH;
    if (currentDirection.equals(Direction.SOUTH)) return Direction.WEST;
    if (currentDirection.equals(Direction.WEST)) return Direction.NORTH;
    else {
      log.error("Invalid Direction");
      throw new RoverException(
          Output.builder()
              .finalDirection(this.getCurrentDirection().getSymbol())
              .finalPosition(this.getFinalLocation())
              .finalStatus(Status.INVALID_DIRECTION.getMessage())
              .build());
    }
  }

  public Direction getLeftDirection(Direction currentDirection) throws RoverException {
    if (currentDirection.equals(Direction.NORTH)) return Direction.WEST;
    if (currentDirection.equals(Direction.EAST)) return Direction.NORTH;
    if (currentDirection.equals(Direction.SOUTH)) return Direction.EAST;
    if (currentDirection.equals(Direction.WEST)) return Direction.SOUTH;
    else {
      log.error("Invalid Direction");
      throw new RoverException(
          Output.builder()
              .finalDirection(this.getCurrentDirection().getSymbol())
              .finalPosition(this.getFinalLocation())
              .finalStatus(Status.INVALID_DIRECTION.getMessage())
              .build());
    }
  }

  public boolean encounterObstacle(Grid grid) {
    return grid.getObstacles()
        .contains(
            Node.builder()
                .horizontalPosition(this.getHorizontalPosition())
                .verticalPosition(this.getVerticalPosition())
                .build());
  }

  public boolean isOutOfBound(Node currentPosition, Grid grid) {
    return currentPosition.getHorizontalPosition() >= grid.getHorizontalSize()
        || currentPosition.getHorizontalPosition() <= -grid.getHorizontalSize()
        || currentPosition.getVerticalPosition() >= grid.getVerticalSize()
        || currentPosition.getVerticalPosition() <= -grid.getVerticalSize();
  }
}
