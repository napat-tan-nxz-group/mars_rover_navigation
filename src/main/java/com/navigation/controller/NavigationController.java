package com.navigation.controller;

import com.navigation.exception.RoverException;
import com.navigation.model.NavigateRequestBody;
import com.navigation.model.Node;
import com.navigation.model.Output;
import com.navigation.service.NavigationService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationController {

  private final NavigationService navigationService;

  @Autowired
  public NavigationController(NavigationService navigationService) {
    this.navigationService = navigationService;
  }

  @GetMapping("rover/navigate")
  public ResponseEntity<Output> navigateRover(@RequestBody NavigateRequestBody requestBody) {
    try {
      List<Node> obstacles = getObstacleNodes(requestBody);
      Output output =
          navigationService.navigateRover(
              requestBody.getGridSize(), obstacles, requestBody.getCommands());
      return ResponseEntity.ok(output);
    } catch (RoverException e) {
      return new ResponseEntity<>(e.getOutput(), e.getStatus());
    }
  }

  public List<Node> getObstacleNodes(NavigateRequestBody requestBody) {
    return Arrays.stream(requestBody.getObstacles())
        .map(NavigateRequestBody::convertTupleToNode)
        .toList();
  }
}
