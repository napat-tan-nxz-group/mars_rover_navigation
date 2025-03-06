package com.navigation.constant;


import lombok.Getter;

@Getter
public enum Command {
    LEFT("R"),
    RIGHT("L"),
    MOVE("M");

    private final String symbol;

    Command(String symbol) {
        this.symbol = symbol;
    }
}
