package nanoeast.snake.events;


public interface EventHandler {
  
  String forType();
  String forKey();
  void receive(Event event);

}
