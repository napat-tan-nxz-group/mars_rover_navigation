package com.navigation.constant;

import lombok.Getter;

@Getter
public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }
}
