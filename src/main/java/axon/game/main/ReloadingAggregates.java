package axon.game.main;

import java.io.File;
import java.util.UUID;

import lombok.extern.log4j.Log4j;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.unitofwork.DefaultUnitOfWork;

import axon.game.domain.ResistanceGame;

@Log4j
public class ReloadingAggregates {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {
    EventStore eventStore = new FileSystemEventStore(
        new SimpleEventFileResolver(new File("./axon-events/")));

    EventSourcingRepository repository = new EventSourcingRepository(
        ResistanceGame.class, eventStore);

    DefaultUnitOfWork.startAndGet();
    UUID uuid = UUID.fromString("6d7498ce-358b-4ca6-82ee-1c812f6095f9");
    ResistanceGame game = (ResistanceGame) repository.load(uuid);
    log.info("# of players: " + game.getPlayers().size());
  }
}
