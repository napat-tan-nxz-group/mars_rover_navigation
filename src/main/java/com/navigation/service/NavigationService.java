package com.navigation.service;

import com.navigation.constant.Direction;
import com.navigation.model.Grid;
import com.navigation.model.Node;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NavigationService {
  private static final int STARTING_HORIZONTAL_POSITION = 0;
  private static final int STARTING_VERTICAL_POSITION = 0;
  private static final Direction STARTING_DIRECTION = Direction.NORTH;

  private Grid currentGrid;

  public void navigateRover(Integer gridSize, List<Node> obstacles, String command) {
    //    TODO
  }
}
