package axon.game.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateGameCommand {
  
  @TargetAggregateIdentifier
  private final String id;

}
