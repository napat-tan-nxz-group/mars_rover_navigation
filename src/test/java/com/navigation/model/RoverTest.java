package com.navigation.model;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoverTest {

  private Rover sampleRover;
  private Grid sampleGrid;

  @BeforeEach
  void setup() {
    sampleRover = Rover.builder().roverSpeed(1).currentDirection(Direction.NORTH).build();
    sampleRover.setHorizontalPosition(0);
    sampleRover.setVerticalPosition(0);
    sampleGrid = Grid.builder().horizontalSize(5).verticalSize(5).build();
  }

  @Test
  void moveForward_givenNorthDirection_increaseVerticalPosition() {
    sampleRover.moveForward(sampleGrid, Direction.NORTH);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
    assertEquals(Direction.NORTH, sampleRover.getCurrentDirection());
  }

  @Test
  void moveForward_givenEastDirection_increaseHorizontalPosition() {
    sampleRover.moveForward(sampleGrid, Direction.EAST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(1, sampleRover.getHorizontalPosition());
    assertEquals(Direction.EAST, sampleRover.getCurrentDirection());
  }

  @Test
  void moveForward_givenSouthDirection_decreaseVerticalPosition() {
    sampleRover.moveForward(sampleGrid, Direction.SOUTH);

    assertEquals(-1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
    assertEquals(Direction.SOUTH, sampleRover.getCurrentDirection());
  }

  @Test
  void moveForward_givenWestDirection_decreaseHorizontalPosition() {
    sampleRover.moveForward(sampleGrid, Direction.WEST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(-1, sampleRover.getHorizontalPosition());
    assertEquals(Direction.WEST, sampleRover.getCurrentDirection());
  }

  @Test
  void moveForward_givenNorthDirectionAndMoreRoverSpeed_increaseVerticalPositionMore() {
    sampleRover.setRoverSpeed(2);
    sampleRover.moveForward(sampleGrid, Direction.NORTH);

    assertEquals(2, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
    assertEquals(Direction.NORTH, sampleRover.getCurrentDirection());
  }
}
