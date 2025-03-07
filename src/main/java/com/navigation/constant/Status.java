package com.navigation.constant;

import lombok.Getter;

@Getter
public enum Status {
  SUCCESS("Success"),
  BLOCKED("Obstacle encountered"),
  OUTBOUND("Out of bounds"),
  INVALID_COMMAND("Invalid command"),
  INVALID_DIRECTION("Invalid direction"),
  ;

  private final String message;

  Status(String message) {
    this.message = message;
  }
}
