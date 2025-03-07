package com.navigation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.model.Grid;
import com.navigation.model.Rover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NavigationServiceTest {

  private Grid sampleGrid;
  private Rover sampleRover;
  @InjectMocks private NavigationService navigationService;

  @BeforeEach
  void setup() {
    sampleGrid = Grid.builder().verticalSize(5).horizontalSize(5).build();
    sampleRover =
        Rover.builder()
            .horizontalPosition(0)
            .verticalPosition(0)
            .roverSpeed(1)
            .currentDirection(Direction.NORTH)
            .build();
  }

  @Test
  void isOutOfBound_whenRoverLocationInsideGrid_returnFalse() {
    sampleRover.setVerticalPosition(1);
    sampleRover.setHorizontalPosition(1);

    assertFalse(navigationService.isOutOfBound(sampleRover, sampleGrid));
  }

  @Test
  void isOutOfBound_whenRoverLocationOutsideGrid_returnTrue() {
    sampleRover.setVerticalPosition(6);
    sampleRover.setHorizontalPosition(1);

    assertTrue(navigationService.isOutOfBound(sampleRover, sampleGrid));
  }

  @Test
  void interpretCommand_givenM_returnMoveCommand() {
    assertEquals(Command.MOVE, navigationService.interpretCommand("M"));
  }

  @Test
  void interpretCommand_givenR_returnRightCommand() {
    assertEquals(Command.RIGHT, navigationService.interpretCommand("R"));
  }

  @Test
  void interpretCommand_givenL_returnLeftCommand() {
    assertEquals(Command.LEFT, navigationService.interpretCommand("L"));
  }

  @Test
  void interpretCommand_givenOtherChar_returnInvalidCommand() {
    assertEquals(Command.INVALID, navigationService.interpretCommand("G"));
  }

  @Test
  void moveForward_givenNorthDirection_increaseVerticalPosition() {
    navigationService.moveForward(sampleRover, Direction.NORTH);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenEastDirection_increaseHorizontalPosition() {
    navigationService.moveForward(sampleRover, Direction.EAST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenSouthDirection_decreaseVerticalPosition() {
    navigationService.moveForward(sampleRover, Direction.SOUTH);

    assertEquals(-1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenWestDirection_decreaseHorizontalPosition() {
    navigationService.moveForward(sampleRover, Direction.WEST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(-1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenNorthDirectionAndMoreRoverSpeed_increaseVerticalPositionMore() {
    sampleRover.setRoverSpeed(2);
    navigationService.moveForward(sampleRover, Direction.NORTH);

    assertEquals(2, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void getRightDirection_givenNorthDirection_returnEast() {
    assertEquals(Direction.EAST, navigationService.getRightDirection(Direction.NORTH));
  }

  @Test
  void getRightDirection_givenEastDirection_returnSouth() {
    assertEquals(Direction.SOUTH, navigationService.getRightDirection(Direction.EAST));
  }

  @Test
  void getRightDirection_givenSouthDirection_returnWest() {
    assertEquals(Direction.WEST, navigationService.getRightDirection(Direction.SOUTH));
  }

  @Test
  void getRightDirection_givenWestDirection_returnNorth() {
    assertEquals(Direction.NORTH, navigationService.getRightDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenNorthDirection_returnWest() {
    assertEquals(Direction.WEST, navigationService.getLeftDirection(Direction.NORTH));
  }

  @Test
  void getLeftDirection_givenWestDirection_returnSouth() {
    assertEquals(Direction.SOUTH, navigationService.getLeftDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenSouthDirection_returnEast() {
    assertEquals(Direction.EAST, navigationService.getLeftDirection(Direction.SOUTH));
  }

  @Test
  void getLeftDirection_givenEastDirection_returnNorth() {
    assertEquals(Direction.NORTH, navigationService.getLeftDirection(Direction.EAST));
  }

  @Test
  void executeCommand_givenMoveCommand_performMoveForward() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, Command.MOVE);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void executeCommand_givenRightCommand_performTurnRight() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, Command.RIGHT);

    assertEquals(Direction.EAST, sampleRover.getCurrentDirection());
  }

  @Test
  void executeCommand_givenLeftCommand_performTurnLeft() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, Command.LEFT);

    assertEquals(Direction.WEST, sampleRover.getCurrentDirection());
  }
}
