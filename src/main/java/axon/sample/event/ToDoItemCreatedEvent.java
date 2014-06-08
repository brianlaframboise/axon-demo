package axon.sample.event;

import lombok.Data;

@Data
public class ToDoItemCreatedEvent {

  private final String todoId;
  private final String description;

}