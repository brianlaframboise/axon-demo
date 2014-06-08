package axon.game.event;

import lombok.Data;
import axon.game.domain.Player;

@Data
public class PlayerDeregisteredEvent {

  private final String id;
  private final Player player;
}
