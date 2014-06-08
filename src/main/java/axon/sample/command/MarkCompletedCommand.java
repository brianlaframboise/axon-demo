package axon.sample.command;

import lombok.Data;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Data
public class MarkCompletedCommand {
  
  @TargetAggregateIdentifier
  private final String todoId;

  
}