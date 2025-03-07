package com.navigation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.constant.Status;
import com.navigation.exception.RoverException;
import com.navigation.model.Grid;
import com.navigation.model.Node;
import com.navigation.model.Output;
import com.navigation.model.Rover;
import java.util.List;
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
  void isOutOfBound_whenRoverLocationInsideGrid_returnFalse() {
    sampleRover.setVerticalPosition(1);
    sampleRover.setHorizontalPosition(1);

    assertFalse(navigationService.isOutOfBound(sampleRover, sampleGrid));
  }

  @Test
  void isOutOfBound_whenRoverLocationOutsideGrid_returnTrue() {
    sampleRover.setVerticalPosition(5);
    sampleRover.setHorizontalPosition(1);

    assertTrue(navigationService.isOutOfBound(sampleRover, sampleGrid));

    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(-5);

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
  void moveForward_givenNorthDirection_increaseVerticalPosition() throws RoverException {
    navigationService.moveForward(sampleRover, sampleGrid, Direction.NORTH);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenEastDirection_increaseHorizontalPosition() throws RoverException {
    navigationService.moveForward(sampleRover, sampleGrid, Direction.EAST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenSouthDirection_decreaseVerticalPosition() throws RoverException {
    navigationService.moveForward(sampleRover, sampleGrid, Direction.SOUTH);

    assertEquals(-1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenWestDirection_decreaseHorizontalPosition() throws RoverException {
    navigationService.moveForward(sampleRover, sampleGrid, Direction.WEST);

    assertEquals(0, sampleRover.getVerticalPosition());
    assertEquals(-1, sampleRover.getHorizontalPosition());
  }

  @Test
  void moveForward_givenNorthDirectionAndMoreRoverSpeed_increaseVerticalPositionMore()
      throws RoverException {
    sampleRover.setRoverSpeed(2);
    navigationService.moveForward(sampleRover, sampleGrid, Direction.NORTH);

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
  void executeCommand_givenMoveCommand_performMoveForward() throws RoverException {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, sampleGrid, Command.MOVE);

    assertEquals(1, sampleRover.getVerticalPosition());
    assertEquals(0, sampleRover.getHorizontalPosition());
  }

  @Test
  void executeCommand_givenRightCommand_performTurnRight() throws RoverException {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, sampleGrid, Command.RIGHT);

    assertEquals(Direction.EAST, sampleRover.getCurrentDirection());
  }

  @Test
  void executeCommand_givenLeftCommand_performTurnLeft() throws RoverException {
    sampleRover.setCurrentDirection(Direction.NORTH);
    navigationService.executeCommand(sampleRover, sampleGrid, Command.LEFT);

    assertEquals(Direction.WEST, sampleRover.getCurrentDirection());
  }

  @Test
  void encounterObstacle_givenRoverOnObstacle_returnTrue() {
    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(3);

    assertTrue(navigationService.encounterObstacle(sampleGrid, sampleRover));
  }

  @Test
  void encounterObstacle_givenAnywhereElse_returnFalse() {
    sampleRover.setVerticalPosition(3);
    sampleRover.setHorizontalPosition(1);

    assertFalse(navigationService.encounterObstacle(sampleGrid, sampleRover));
  }

  @Test
  void navigateRover_givenCorrectCommand_returnSuccessfulOutput() {

    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().horizontalPosition(0).verticalPosition(1).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.SUCCESS.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(5, obstacles, "LMLMLMLMM");

    //    the output is different  because the example's starting position is set x=1 and y=2
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_whenRoverRunningInCircle_returnSuccessfulOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(2).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(0).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.SUCCESS.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.EAST.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(10, obstacles, "MMMLMMMLMMMLMMM");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenRunningIntoObstacle_returnBlockedOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(3).horizontalPosition(3).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.BLOCKED.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(10, obstacles, "RMMMLMMM");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_whenGoOverGrid_returnOutboundOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(4).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.OUTBOUND.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(5, obstacles, "MMMMMMMM");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenInvalidCommand_returnInvalidOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(2).horizontalPosition(2).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.INVALID_COMMAND.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(5, obstacles, "RMMLMMEMMRL");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenSmallestGridAndMoveCommand_returnOutboundOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(0).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.OUTBOUND.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.EAST.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(1, obstacles, "RMMLRM");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenSmallestGridAndTurningCommand_returnSuccessfulOutput() {
    List<Node> obstacles =
        List.of(Node.builder().verticalPosition(3).horizontalPosition(3).build());
    Node finalNode = Node.builder().verticalPosition(0).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.SUCCESS.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(1, obstacles, "RRRRLLLLRL");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenObstaclesAllOverGridAndMoveCommand_returnBlockedOutput() {

    List<Node> obstacles =
        List.of(
            Node.builder().horizontalPosition(0).verticalPosition(1).build(),
            Node.builder().horizontalPosition(1).verticalPosition(1).build(),
            Node.builder().horizontalPosition(1).verticalPosition(0).build(),
            Node.builder().horizontalPosition(0).verticalPosition(1).build(),
            Node.builder().horizontalPosition(0).verticalPosition(-1).build(),
            Node.builder().horizontalPosition(-1).verticalPosition(-1).build(),
            Node.builder().horizontalPosition(-1).verticalPosition(0).build(),
            Node.builder().horizontalPosition(0).verticalPosition(-1).build());

    Node finalNode = Node.builder().verticalPosition(1).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.BLOCKED.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.NORTH.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(2, obstacles, "MRLM");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void navigateRover_givenObstaclesAllOverGridAndTurningCommand_returnSuccessfulOutput() {
    List<Node> obstacles =
        List.of(
            Node.builder().horizontalPosition(0).verticalPosition(1).build(),
            Node.builder().horizontalPosition(1).verticalPosition(1).build(),
            Node.builder().horizontalPosition(1).verticalPosition(0).build(),
            Node.builder().horizontalPosition(0).verticalPosition(1).build(),
            Node.builder().horizontalPosition(0).verticalPosition(-1).build(),
            Node.builder().horizontalPosition(-1).verticalPosition(-1).build(),
            Node.builder().horizontalPosition(-1).verticalPosition(0).build(),
            Node.builder().horizontalPosition(0).verticalPosition(-1).build());

    Node finalNode = Node.builder().verticalPosition(0).horizontalPosition(0).build();

    Output expectedOutput =
        Output.builder()
            .finalStatus(Status.SUCCESS.getMessage())
            .finalPosition(finalNode.getFinalLocation())
            .finalDirection(Direction.WEST.getSymbol())
            .build();

    Output actualOutput = navigationService.navigateRover(2, obstacles, "RLLRRLL");

    assertEquals(expectedOutput, actualOutput);
  }
}
