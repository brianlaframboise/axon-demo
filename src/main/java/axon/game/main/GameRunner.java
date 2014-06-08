package axon.game.main;

import java.io.File;
import java.util.UUID;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;

import axon.game.command.CreateGameCommand;
import axon.game.command.DeregisterPlayerCommand;
import axon.game.command.RegisterPlayerCommand;
import axon.game.command.StartGameCommand;
import axon.game.domain.Player;
import axon.game.domain.ResistanceGame;

public class GameRunner {

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {
    // let's start with the Command Bus
    CommandBus commandBus = new SimpleCommandBus();

    // the CommandGateway provides a friendlier API
    CommandGateway gateway = new DefaultCommandGateway(commandBus);

    // we'll store Events on the FileSystem, in the "axon-events/" folder
    EventStore eventStore = new FileSystemEventStore(
        new SimpleEventFileResolver(new File("./axon-events/")));

    // a Simple Event Bus will do
    EventBus eventBus = new SimpleEventBus();

    // we need to configure the repository
    EventSourcingRepository repository = new EventSourcingRepository(
        ResistanceGame.class, eventStore);
    repository.setEventBus(eventBus);

    // Axon needs to know that our ResistanceGame Aggregate can handle commands
    AggregateAnnotationCommandHandler.subscribe(ResistanceGame.class,
        repository, commandBus);

    AnnotationEventListenerAdapter.subscribe(new ResistanceGame(), eventBus);
    AnnotationEventListenerAdapter.subscribe(new GameAnalyzer(), eventBus);
    AnnotationEventListenerAdapter.subscribe(new SqlAdapter(), eventBus);

    // and let's send some Commands on the CommandBus.
    final String gameId = UUID.randomUUID().toString();
    gateway.send(new CreateGameCommand(gameId));

    gateway.send(new RegisterPlayerCommand(gameId, new Player("Brian")));
    gateway.send(new RegisterPlayerCommand(gameId, new Player("Jason")));
    gateway.send(new RegisterPlayerCommand(gameId, new Player("Matt")));
    gateway.send(new RegisterPlayerCommand(gameId, new Player("Jon")));
    gateway.send(new RegisterPlayerCommand(gameId, new Player("Naveen")));
    gateway.send(new DeregisterPlayerCommand(gameId, new Player("Jason")));
    gateway.send(new RegisterPlayerCommand(gameId, new Player("Daniil")));

    gateway.send(new StartGameCommand(gameId));
  }

}
