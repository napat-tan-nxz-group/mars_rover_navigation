package com.navigation.service;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.model.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NavigationService {
  private static final Integer STARTING_HORIZONTAL_POSITION = 0;
  private static final Integer STARTING_VERTICAL_POSITION = 0;
  private static final Direction STARTING_DIRECTION = Direction.NORTH;
  private static final Integer ROVER_SPEED = 1;

  private Grid currentGrid;
  private Rover rover;

  public void navigateRover(Integer gridSize, List<Node> obstacles, String command) {
    log.info("initializing Grid and Rover");
    initializeGrid(gridSize, obstacles);
    initializeRover();
  }

  public boolean isOutOfBound(Rover rover, Grid grid) {
    return rover.getHorizontalPosition() > grid.getHorizontalSize()
        || rover.getHorizontalPosition() < -grid.getHorizontalSize()
        || rover.getVerticalPosition() > grid.getVerticalSize()
        || rover.getVerticalPosition() < -grid.getVerticalSize();
  }

  private void initializeGrid(Integer gridSize, List<Node> obstacles) {
    currentGrid =
        Grid.builder().horizontalSize(gridSize).verticalSize(gridSize).obstacles(obstacles).build();
  }

  private void initializeRover() {
    rover =
        Rover.builder()
            .roverSpeed(ROVER_SPEED)
            .currentDirection(STARTING_DIRECTION)
            .verticalPosition(STARTING_VERTICAL_POSITION)
            .horizontalPosition(STARTING_HORIZONTAL_POSITION)
            .build();
  }

  public Command interpretCommand(String commandString) {
    return switch (commandString) {
      case "M" -> Command.MOVE;
      case "L" -> Command.LEFT;
      case "R" -> Command.RIGHT;
      default -> Command.INVALID;
    };
  }

  public void moveForward(Rover rover, Direction newDirection) {
    if (newDirection.equals(Direction.NORTH)) {
      rover.setVerticalPosition(rover.getVerticalPosition() + rover.getRoverSpeed());
    }
    if (newDirection.equals(Direction.EAST)) {
      rover.setHorizontalPosition(rover.getHorizontalPosition() + rover.getRoverSpeed());
    }
    if (newDirection.equals(Direction.SOUTH)) {
      rover.setVerticalPosition(rover.getVerticalPosition() - rover.getRoverSpeed());
    }
    if (newDirection.equals(Direction.WEST)) {
      rover.setHorizontalPosition(rover.getHorizontalPosition() - rover.getRoverSpeed());
    }
    log.info("Rover is now at: {}, {}", rover.getVerticalPosition(), rover.getHorizontalPosition());
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

  public void executeCommand(Rover rover, Command command) {
    if (command.equals(Command.MOVE)) {
      log.info("Moving Forward");
      moveForward(rover, rover.getCurrentDirection()); // move forward command
    }
    //    change direction command
    if (command.equals(Command.LEFT)) {
      Direction newDirection = getLeftDirection(rover.getCurrentDirection());
      log.info("Turning left to Direction: {}", newDirection);
      rover.setCurrentDirection(newDirection);
    }
    if (command.equals(Command.RIGHT)) {
      Direction newDirection = getRightDirection(rover.getCurrentDirection());
      log.info("Turning right to Direction: {}", newDirection);
      rover.setCurrentDirection(newDirection);
    }
  }
}
