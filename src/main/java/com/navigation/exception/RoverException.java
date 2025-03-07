package com.navigation.exception;

import com.navigation.model.Output;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoverException extends Throwable {
  private final transient Output output;

  public RoverException(Output output) {
    this.output = output;
  }
}
