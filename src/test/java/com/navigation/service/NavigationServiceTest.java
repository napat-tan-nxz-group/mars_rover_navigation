package com.navigation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.navigation.constant.Command;
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
    sampleRover = Rover.builder().horizontalPosition(0).verticalPosition(0).build();
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
  void interpretCommand_givenOtherChar_throwException() {
    assertThrows(IllegalArgumentException.class, () -> navigationService.interpretCommand("G"));
  }
}
