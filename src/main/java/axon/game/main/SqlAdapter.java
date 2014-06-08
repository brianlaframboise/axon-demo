package axon.game.main;

import lombok.extern.log4j.Log4j;

import org.axonframework.eventhandling.annotation.EventHandler;

import axon.game.event.GameCreatedEvent;
import axon.game.event.GameStartedEvent;
import axon.game.event.PlayerDeregisteredEvent;
import axon.game.event.PlayerRegisteredEvent;

@Log4j
public class SqlAdapter {

  @EventHandler
  public void on(GameCreatedEvent event) {
    log.info("INSERT (" + event.getId() + ") INTO Games");
  }

  @EventHandler
  public void on(PlayerRegisteredEvent event) {
    log.info("INSERT (" + event.getId() + ", " + event.getPlayer().getName()
        + ") INTO Registrations");
  }

  @EventHandler
  public void on(PlayerDeregisteredEvent event) {
    log.info("DELETE from Registrations where id = " + event.getId());
  }

  @EventHandler
  public void on(GameStartedEvent event) {
    log.info("UPDATE Games SET Started=true where id = " + event.getId());
  }

}
