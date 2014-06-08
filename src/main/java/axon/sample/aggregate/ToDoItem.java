package axon.sample.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import axon.sample.command.CreateToDoItemCommand;
import axon.sample.command.MarkCompletedCommand;
import axon.sample.event.ToDoItemCompletedEvent;
import axon.sample.event.ToDoItemCreatedEvent;

@NoArgsConstructor
public class ToDoItem extends AbstractAnnotatedAggregateRoot<ToDoItem> {

  @AggregateIdentifier
  private String id;
  @Getter
  private boolean completed;

  @CommandHandler
  public ToDoItem(CreateToDoItemCommand command) {
    System.out.println("Executing: " + command);
    apply(new ToDoItemCreatedEvent(command.getTodoId(), command.getDescription()));
  }

  @EventHandler
  public void on(ToDoItemCreatedEvent event) {
    System.out.println("Applying: " + event);
    id = event.getTodoId();
  }

  @EventHandler
  public void on(ToDoItemCompletedEvent event) {
    System.out.println("Applying: " + event);
    completed = true;
  }

  @CommandHandler
  public void markCompleted(MarkCompletedCommand command) {
    System.out.println("Executing: " + command);
    apply(new ToDoItemCompletedEvent(id));
  }
}
