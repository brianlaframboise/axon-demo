package axon.game.event;

import java.util.Set;

import lombok.Data;
import axon.game.domain.Player;

@Data
public class GameStartedEvent {

  private final String id;
  private final Set<Player> players;
}
