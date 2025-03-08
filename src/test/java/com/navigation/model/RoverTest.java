package com.navigation.model;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Direction;
import com.navigation.exception.RoverException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoverTest {
  private Grid sampleGrid;
  private Rover sampleRover;

  @BeforeEach
  void setup() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());

    sampleGrid = Grid.builder().verticalSize(5).horizontalSize(5).obstacles(obstacles).build();
    sampleRover =
        Rover.builder()
            .horizontalPosition(0)
            .verticalPosition(0)
            .roverSpeed(1)
            .currentDirection(Direction.NORTH)
            .build();
  }

  @Test
  void moveForward_givenNorthDirection_increaseVerticalPosition() throws RoverException {
    sampleRover.moveForward(sampleGrid, Direction.NORTH);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenEastDirection_increaseHorizontalPosition() throws RoverException {
    sampleRover.moveForward(sampleGrid, Direction.EAST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenSouthDirection_decreaseVerticalPosition() throws RoverException {
    sampleRover.moveForward(sampleGrid, Direction.SOUTH);

    assertEquals(-1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenWestDirection_decreaseHorizontalPosition() throws RoverException {
    sampleRover.moveForward(sampleGrid, Direction.WEST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(-1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenNorthDirectionAndMoreRoverSpeed_increaseVerticalPositionMore()
      throws RoverException {
    sampleRover.setRoverSpeed(2);
    sampleRover.moveForward(sampleGrid, Direction.NORTH);

    assertEquals(2, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void reposition_giveNorthDirection_returnIncreasedVerticalPosition() throws RoverException {
    Node newPosition = sampleRover.reposition(Direction.NORTH);
    assertEquals(sampleRover.getHorizontalPosition(), newPosition.getHorizontalPosition());
    assertEquals(
        sampleRover.getVerticalPosition() + sampleRover.getRoverSpeed(),
        newPosition.getVerticalPosition());
  }

  @Test
  void reposition_giveEastDirection_returnIncreasedHorizontalPosition() throws RoverException {
    Node newPosition = sampleRover.reposition(Direction.EAST);
    assertEquals(
        sampleRover.getHorizontalPosition() + sampleRover.getRoverSpeed(),
        newPosition.getHorizontalPosition());
    assertEquals(sampleRover.getVerticalPosition(), newPosition.getVerticalPosition());
  }

  @Test
  void reposition_giveSouthDirection_returnDecreasedVerticalPosition() throws RoverException {
    Node newPosition = sampleRover.reposition(Direction.SOUTH);
    assertEquals(sampleRover.getHorizontalPosition(), newPosition.getHorizontalPosition());
    assertEquals(
        sampleRover.getVerticalPosition() - sampleRover.getRoverSpeed(),
        newPosition.getVerticalPosition());
  }

  @Test
  void reposition_giveWestDirection_returnDecreasedVerticalPosition() throws RoverException {
    Node newPosition = sampleRover.reposition(Direction.WEST);
    assertEquals(
        sampleRover.getHorizontalPosition() - sampleRover.getRoverSpeed(),
        newPosition.getHorizontalPosition());
    assertEquals(sampleRover.getVerticalPosition(), newPosition.getVerticalPosition());
  }

  @Test
  void getRightDirection_givenNorthDirection_returnEast() throws RoverException {
    assertEquals(Direction.EAST, sampleRover.getRightDirection(Direction.NORTH));
  }

  @Test
  void getRightDirection_givenEastDirection_returnSouth() throws RoverException {
    assertEquals(Direction.SOUTH, sampleRover.getRightDirection(Direction.EAST));
  }

  @Test
  void getRightDirection_givenSouthDirection_returnWest() throws RoverException {
    assertEquals(Direction.WEST, sampleRover.getRightDirection(Direction.SOUTH));
  }

  @Test
  void getRightDirection_givenWestDirection_returnNorth() throws RoverException {
    assertEquals(Direction.NORTH, sampleRover.getRightDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenNorthDirection_returnWest() throws RoverException {
    assertEquals(Direction.WEST, sampleRover.getLeftDirection(Direction.NORTH));
  }

  @Test
  void getLeftDirection_givenWestDirection_returnSouth() throws RoverException {
    assertEquals(Direction.SOUTH, sampleRover.getLeftDirection(Direction.WEST));
  }

  @Test
  void getLeftDirection_givenSouthDirection_returnEast() throws RoverException {
    assertEquals(Direction.EAST, sampleRover.getLeftDirection(Direction.SOUTH));
  }

  @Test
  void getLeftDirection_givenEastDirection_returnNorth() throws RoverException {
    assertEquals(Direction.NORTH, sampleRover.getLeftDirection(Direction.EAST));
  }

  @Test
  void encounterObstacle_givenRoverOnObstacle_returnTrue() {
    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(3);

    assertTrue(sampleRover.encounterObstacle(sampleGrid));
  }

  @Test
  void encounterObstacle_givenAnywhereElse_returnFalse() {
    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(1);

    assertFalse(sampleRover.encounterObstacle(sampleGrid));
  }

  @Test
  void isOutOfBound_whenRoverLocationInsideGrid_returnFalse() {
    sampleRover.setVerticalPosition(1);
    sampleRover.setHorizontalPosition(1);

    assertFalse(sampleRover.isOutOfBound(sampleRover, sampleGrid));
  }

  @Test
  void isOutOfBound_whenRoverLocationOutsideGrid_returnTrue() {
    sampleRover.setVerticalPosition(5);
    sampleRover.setHorizontalPosition(1);

    assertTrue(sampleRover.isOutOfBound(sampleRover, sampleGrid));

    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(-5);

    assertTrue(sampleRover.isOutOfBound(sampleRover, sampleGrid));
  }
}
