package com.navigation.constant;

import lombok.Getter;

@Getter
public enum Command {
  LEFT("L"),
  RIGHT("R"),
  MOVE("M"),
  ;

  private final String symbol;

  Command(String symbol) {
    this.symbol = symbol;
  }
}
