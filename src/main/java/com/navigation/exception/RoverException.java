package com.navigation.exception;

import com.navigation.model.Output;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class RoverException extends Throwable {
  private final transient Output output;
  private final HttpStatus status;

  public RoverException(Output output, HttpStatus status) {
    this.output = output;
    this.status = status;
  }
}
