package axon.game.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import axon.game.command.CreateGameCommand;
import axon.game.command.DeregisterPlayerCommand;
import axon.game.command.RegisterPlayerCommand;
import axon.game.command.StartGameCommand;
import axon.game.event.GameCreatedEvent;
import axon.game.event.GameStartedEvent;
import axon.game.event.PlayerDeregisteredEvent;
import axon.game.event.PlayerRegisteredEvent;

@NoArgsConstructor
@Log4j
public class ResistanceGame extends
    AbstractAnnotatedAggregateRoot<ResistanceGame> {

  @AggregateIdentifier
  private String id;

  private final Set<Player> players = new HashSet<>();

  @CommandHandler
  public ResistanceGame(CreateGameCommand command) {
    log.info("Executing: " + command);
    apply(new GameCreatedEvent(command.getId()));
  }

  @CommandHandler
  public void register(RegisterPlayerCommand command) {
    log.info("Executing: " + command);
    apply(new PlayerRegisteredEvent(command.getId(), command.getPlayer()));
  }

  @CommandHandler
  public void deregister(DeregisterPlayerCommand command) {
    log.info("Executing: " + command);
    apply(new PlayerDeregisteredEvent(command.getId(), command.getPlayer()));
  }

  @CommandHandler
  public void start(StartGameCommand command) {
    log.info("Executing: " + command);
    if (players.size() == 5) {
      apply(new GameStartedEvent(command.getId(), new HashSet<Player>(players)));
    } else {
      throw new IllegalStateException("Cannot start a game with # players: "
          + players.size());
    }
  }

  @EventHandler
  public void on(GameCreatedEvent event) {
    log.info("Handling: " + event);
    id = event.getId();
  }

  @EventHandler
  public void on(PlayerRegisteredEvent event) {
    log.info("Handling: " + event);
    players.add(event.getPlayer());
  }

  @EventHandler
  public void on(PlayerDeregisteredEvent event) {
    log.info("Handling: " + event);
    players.remove(event.getPlayer());
  }

  @EventHandler
  public void on(GameStartedEvent event) {
    log.info("Handling: " + event);
  }

}
