package axon.sample;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import axon.sample.aggregate.ToDoItem;
import axon.sample.command.CreateToDoItemCommand;
import axon.sample.command.MarkCompletedCommand;
import axon.sample.event.ToDoItemCompletedEvent;
import axon.sample.event.ToDoItemCreatedEvent;

public class ToDoItemTest {

  private FixtureConfiguration<ToDoItem> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = Fixtures.newGivenWhenThenFixture(ToDoItem.class);
  }

  @Test
  public void testCreateToDoItem() throws Exception {
    fixture.given().when(new CreateToDoItemCommand("todo1", "need to implement the aggregate"))
        .expectEvents(new ToDoItemCreatedEvent("todo1", "need to implement the aggregate"));
  }

  @Test
  public void testMarkToDoItemAsCompleted() throws Exception {
    fixture
        .given(new ToDoItemCreatedEvent("todo1", "need to implement the aggregate"))
        .when(new MarkCompletedCommand("todo1"))
        .expectEvents(new ToDoItemCompletedEvent("todo1"));
  }
}
