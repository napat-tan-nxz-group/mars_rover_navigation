package com.navigation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Command;
import com.navigation.constant.Direction;
import com.navigation.constant.Status;
import com.navigation.model.Node;
import com.navigation.model.Output;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NavigationServiceTest {

  @InjectMocks private NavigationService navigationService;

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
