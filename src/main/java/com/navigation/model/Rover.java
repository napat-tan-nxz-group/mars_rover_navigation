package com.navigation.model;

import com.navigation.constant.Direction;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Rover extends Node {
  private Direction currentDirection;
  private Integer roverSpeed;
}
