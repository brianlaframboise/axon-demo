package axon.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import axon.game.command.CreateGameCommand;
import axon.game.command.DeregisterPlayerCommand;
import axon.game.command.RegisterPlayerCommand;
import axon.game.command.StartGameCommand;
import axon.game.domain.Player;
import axon.game.domain.ResistanceGame;
import axon.game.event.GameCreatedEvent;
import axon.game.event.GameStartedEvent;
import axon.game.event.PlayerDeregisteredEvent;
import axon.game.event.PlayerRegisteredEvent;

public class ResistanceGameTest {

  private FixtureConfiguration<ResistanceGame> fixture;

  @Before
  public void setup() {
    fixture = Fixtures.newGivenWhenThenFixture(ResistanceGame.class);
  }

  @Test
  public void create_game() {
    fixture.
        givenNoPriorActivity().
        when(new CreateGameCommand("id")).
        expectEvents(new GameCreatedEvent("id"));
  }

  @Test
  public void register_player() {
    fixture.
        given(new GameCreatedEvent("id")).
        when(new RegisterPlayerCommand("id", new Player("Brian"))).
        expectEvents(new PlayerRegisteredEvent("id", new Player("Brian")));
  }

  @Test
  public void deregister_player() {
    fixture.
        given(new GameCreatedEvent("id"),
            new PlayerRegisteredEvent("id", new Player("Brian"))).
        when(new DeregisterPlayerCommand("id", new Player("Brian"))).
        expectEvents(new PlayerDeregisteredEvent("id", new Player("Brian")));
  }

  @Test
  public void games_must_have_5_players() {
    fixture
        .given(startGameEvents("id", 4))
        .when(new StartGameCommand("id"))
        .expectEvents();
  }
  
  @Test
  public void start_game_with_5_players() {
    Set<Player> players = new HashSet<>();
    for (int i = 1; i < 6; i++) {
      players.add(new Player("Player" + i));
    }

    fixture
        .given(startGameEvents("id", 5))
        .when(new StartGameCommand("id"))
        .expectEvents(new GameStartedEvent("id", players));
  }

  private List<?> startGameEvents(String id, int players) {
    List<Object> list = new ArrayList<Object>();
    list.add(new GameCreatedEvent(id));
    for (int i = 0; i < players; i++) {
      list.add(new PlayerRegisteredEvent(id, new Player("Player" + (i + 1))));
    }
    return list;
  }
}
