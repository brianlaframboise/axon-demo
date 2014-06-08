package axon.game.main;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.log4j.Log4j;

import org.axonframework.eventhandling.annotation.EventHandler;

import axon.game.domain.Player;
import axon.game.event.GameStartedEvent;
import axon.game.event.PlayerDeregisteredEvent;

@Log4j
public class GameAnalyzer {

  private final Set<Player> deregistered = new HashSet<>();
  private final Set<Player> started = new HashSet<>();

  public GameAnalyzer() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        for (Player player : deregistered) {
          if (!started.contains(player)) {
            log.warn(player + " deregistered and never played");
          }
        }
      }
    });
  }

  @EventHandler
  public void on(PlayerDeregisteredEvent event) {
    deregistered.add(event.getPlayer());
  }

  @EventHandler
  public void on(GameStartedEvent event) {
    started.addAll(event.getPlayers());
    log.info("Game " + event.getId() + " started with players: "
        + event.getPlayers());
  }

}
