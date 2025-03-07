package com.navigation.service;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.constant.Status;
import com.navigation.exception.RoverException;
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

  public Output navigateRover(Integer gridSize, List<Node> obstacles, String commands) {
    try {
      log.info("initializing Grid and Rover");
      initializeGrid(gridSize, obstacles);
      initializeRover();

      for (char c : commands.toCharArray()) {
        Command currentCommand = interpretCommand(String.valueOf(c));
        //      check for invalid command
        if (currentCommand.equals(Command.INVALID)) {
          log.error("Invalid Command");
          return generateOutput(Status.INVALID_COMMAND);
        }
        //      perform command
        executeCommand(rover, currentGrid, currentCommand);
      }
      //    all command processed successfully
      return generateOutput(Status.SUCCESS);
    } catch (RoverException e) {
      return e.getOutput();
    }
  }

  public boolean isOutOfBound(Node currentPosition, Grid grid) {
    return currentPosition.getHorizontalPosition() >= grid.getHorizontalSize()
        || currentPosition.getHorizontalPosition() <= -grid.getHorizontalSize()
        || currentPosition.getVerticalPosition() >= grid.getVerticalSize()
        || currentPosition.getVerticalPosition() <= -grid.getVerticalSize();
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

  public void moveForward(Rover rover, Grid grid, Direction newDirection) throws RoverException {

    Node newPosition = reposition(rover, newDirection);

    //    out of grid case
    if (isOutOfBound(newPosition, grid)) {
      log.error("Rover Out of Bound");
      throw new RoverException(
          Output.builder()
              .finalDirection(rover.getCurrentDirection().getSymbol())
              .finalPosition(rover.getFinalLocation())
              .finalStatus(Status.OUTBOUND.getMessage())
              .build());
    }

    rover.setVerticalPosition(newPosition.getVerticalPosition());
    rover.setHorizontalPosition(newPosition.getHorizontalPosition());

    //    encounter obstacle
    if (encounterObstacle(grid, rover)) {
      throw new RoverException(
          Output.builder()
              .finalDirection(rover.getCurrentDirection().getSymbol())
              .finalPosition(rover.getFinalLocation())
              .finalStatus(Status.BLOCKED.getMessage())
              .build());
    }

    log.info("Rover is now at: {}, {}", rover.getHorizontalPosition(), rover.getVerticalPosition());
  }

  public Node reposition(Rover rover, Direction newDirection) throws RoverException {
    if (newDirection.equals(Direction.NORTH)) {
      return Node.builder()
          .horizontalPosition(rover.getHorizontalPosition())
          .verticalPosition(rover.getVerticalPosition() + rover.getRoverSpeed())
          .build();
    }
    if (newDirection.equals(Direction.EAST)) {
      return Node.builder()
          .horizontalPosition(rover.getHorizontalPosition() + rover.getRoverSpeed())
          .verticalPosition(rover.getVerticalPosition())
          .build();
    }
    if (newDirection.equals(Direction.SOUTH)) {
      return Node.builder()
          .horizontalPosition(rover.getHorizontalPosition())
          .verticalPosition(rover.getVerticalPosition() - rover.getRoverSpeed())
          .build();
    }
    if (newDirection.equals(Direction.WEST)) {
      return Node.builder()
          .horizontalPosition(rover.getHorizontalPosition() - rover.getRoverSpeed())
          .verticalPosition(rover.getVerticalPosition())
          .build();
    } else {
      log.error("Invalid Direction");
      throw new RoverException(
          Output.builder()
              .finalDirection(rover.getCurrentDirection().getSymbol())
              .finalPosition(rover.getFinalLocation())
              .finalStatus(Status.INVALID_DIRECTION.getMessage())
              .build());
    }
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

  public void executeCommand(Rover rover, Grid grid, Command command) throws RoverException {
    if (command.equals(Command.MOVE)) {
      log.info("Moving Forward");
      moveForward(rover, grid, rover.getCurrentDirection()); // move forward command
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

  public boolean encounterObstacle(Grid grid, Rover rover) {
    return grid.getObstacles()
        .contains(
            Node.builder()
                .horizontalPosition(rover.getHorizontalPosition())
                .verticalPosition(rover.getVerticalPosition())
                .build());
  }

  private Output generateOutput(Status status) {
    return Output.builder()
        .finalDirection(rover.getCurrentDirection().getSymbol())
        .finalPosition(rover.getFinalLocation())
        .finalStatus(status.getMessage())
        .build();
  }
}
