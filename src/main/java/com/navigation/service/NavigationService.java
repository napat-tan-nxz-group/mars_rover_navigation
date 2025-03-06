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
    return Command.valueOf(commandString);
  }

  //  TODO: obstacle case
}
