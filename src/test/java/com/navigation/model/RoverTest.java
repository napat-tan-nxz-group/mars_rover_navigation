package com.navigation.model;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoverTest {

  private Rover sampleRover;

  @BeforeEach
  void setup() {
    sampleRover = Rover.builder().roverSpeed(1).currentDirection(Direction.NORTH).build();
    sampleRover.setHorizontalPosition(0);
    sampleRover.setVerticalPosition(0);
  }

  @Test
  void moveForward_givenNorthDirection_increaseVerticalPosition() {
    sampleRover.moveForward(Direction.NORTH);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenEastDirection_increaseHorizontalPosition() {
    sampleRover.moveForward(Direction.EAST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenSouthDirection_decreaseVerticalPosition() {
    sampleRover.moveForward(Direction.SOUTH);

    assertEquals(-1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenWestDirection_decreaseHorizontalPosition() {
    sampleRover.moveForward(Direction.WEST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(-1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenNorthDirectionAndMoreRoverSpeed_increaseVerticalPositionMore() {
    sampleRover.setRoverSpeed(2);
    sampleRover.moveForward(Direction.NORTH);

    assertEquals(2, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void getRightDirection_givenNorthDirection_returnEast() {
    assertEquals(Direction.EAST, sampleRover.getRightDirection(Direction.NORTH));
  }

  @Test
  void getRightDirection_givenEastDirection_returnSouth() {
    assertEquals(Direction.SOUTH, sampleRover.getRightDirection(Direction.EAST));
  }

  @Test
  void getRightDirection_givenSouthDirection_returnWest() {
    assertEquals(Direction.WEST, sampleRover.getRightDirection(Direction.SOUTH));
  }

  @Test
  void getRightDirection_givenWestDirection_returnNorth() {
    assertEquals(Direction.NORTH, sampleRover.getRightDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenNorthDirection_returnWest() {
    assertEquals(Direction.WEST, sampleRover.getLeftDirection(Direction.NORTH));
  }

  @Test
  void getLeftDirection_givenWestDirection_returnSouth() {
    assertEquals(Direction.SOUTH, sampleRover.getLeftDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenSouthDirection_returnEast() {
    assertEquals(Direction.EAST, sampleRover.getLeftDirection(Direction.SOUTH));
  }

  @Test
  void getLeftDirection_givenEastDirection_returnNorth() {
    assertEquals(Direction.NORTH, sampleRover.getLeftDirection(Direction.EAST));
  }

  @Test
  void executeCommand_givenMoveCommand_performMoveForward() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    sampleRover.executeCommand(Command.MOVE);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void executeCommand_givenRightCommand_performTurnRight() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    sampleRover.executeCommand(Command.RIGHT);

    assertEquals(Direction.EAST, sampleRover.getCurrentDirection());
  }

  @Test
  void executeCommand_givenLeftCommand_performTurnLeft() {
    sampleRover.setCurrentDirection(Direction.NORTH);
    sampleRover.executeCommand(Command.LEFT);

    assertEquals(Direction.WEST, sampleRover.getCurrentDirection());
  }
}
