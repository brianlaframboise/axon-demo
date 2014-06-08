package axon.game.command;

import lombok.Data;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import axon.game.domain.Player;

@Data
public class RegisterPlayerCommand {
  
  @TargetAggregateIdentifier
  private final String id;
  private final Player player;

}
