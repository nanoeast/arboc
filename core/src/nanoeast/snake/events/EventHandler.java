package nanoeast.snake.events;

import java.util.Set;

public interface EventHandler {
  
  String forType();
  void receive(Event event);

}
