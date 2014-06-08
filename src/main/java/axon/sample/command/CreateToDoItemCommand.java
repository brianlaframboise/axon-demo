package axon.sample.command;

import lombok.Data;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Data
public class CreateToDoItemCommand {

  @TargetAggregateIdentifier
  private final String todoId;
  private final String description;

}