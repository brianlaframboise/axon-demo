package axon.game.main;

import java.io.File;
import java.util.UUID;

import org.axonframework.domain.AggregateRoot;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.unitofwork.DefaultUnitOfWork;

import axon.game.domain.ResistanceGame;

public class GameAnalyzerRunner {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {
    EventStore eventStore = new FileSystemEventStore(
        new SimpleEventFileResolver(new File("./axon-events/")));

    UUID uuid = UUID.fromString("6d7498ce-358b-4ca6-82ee-1c812f6095f9");
    DomainEventStream stream = eventStore.readEvents("ResistanceGame", uuid);
    while (stream.hasNext()) {
      DomainEventMessage message = stream.next();
    }

    EventSourcingRepository repository = new EventSourcingRepository(
        ResistanceGame.class, eventStore);

    EventBus eventBus = new SimpleEventBus();
    repository.setEventBus(eventBus);

    // AnnotationEventListenerAdapter.subscribe(new ResistanceGame(), eventBus);
    AnnotationEventListenerAdapter.subscribe(new GameAnalyzer(), eventBus);

    DefaultUnitOfWork.startAndGet();
    AggregateRoot root = repository.load(uuid);
  }
}
