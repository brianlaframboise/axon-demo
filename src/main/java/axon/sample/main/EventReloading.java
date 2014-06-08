package axon.sample.main;

import java.io.File;
import java.util.UUID;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.unitofwork.DefaultUnitOfWork;

import axon.sample.aggregate.ToDoItem;

public class EventReloading {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {
    // we'll store Events on the FileSystem, in the "events/" folder
    EventStore eventStore = new FileSystemEventStore(
        new SimpleEventFileResolver(new File("D:\\axon-events")));

    // we need to configure the repository
    EventSourcingRepository repository = new EventSourcingRepository(
        ToDoItem.class, eventStore);

    EventBus eventBus = new SimpleEventBus();

    AnnotationEventListenerAdapter.subscribe(new ToDoItem(), eventBus);
    repository.setEventBus(eventBus);

    UUID uuid = UUID.fromString("6588d4bd-a9c8-4f3c-ad09-74dd1c101ef9");
    DefaultUnitOfWork.startAndGet();
    repository.load(uuid);
  }
}