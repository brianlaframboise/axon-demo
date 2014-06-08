package axon.sample.event;

import lombok.Data;

@Data
public class ToDoItemCompletedEvent {

  private final String todoId;

}