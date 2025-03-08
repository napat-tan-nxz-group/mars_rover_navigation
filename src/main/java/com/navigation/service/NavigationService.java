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
          throw new RoverException(
              Output.builder()
                  .finalDirection(rover.getCurrentDirection().getSymbol())
                  .finalPosition(rover.getFinalLocation())
                  .finalStatus(Status.INVALID_COMMAND.getMessage())
                  .build());
        }
        //      perform command
        rover.executeCommand(currentGrid, currentCommand);
      }
      //    all command processed successfully
      log.info("Navigation Successful");
      return Output.builder()
          .finalDirection(rover.getCurrentDirection().getSymbol())
          .finalPosition(rover.getFinalLocation())
          .finalStatus(Status.SUCCESS.getMessage())
          .build();
    } catch (RoverException e) {
      return e.getOutput();
    }
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
}
