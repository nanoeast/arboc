package nanoeast.snake.screens.boarddisplay;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.Facing;
import nanoeast.snake.screens.BoardDisplayScreen;

public class ChangeFaceEventHandler implements EventHandler {
  
  EngineHeart engineHeart;
  
  public ChangeFaceEventHandler(EngineHeart engineHeart) {
    this.engineHeart = engineHeart;
  }

  @Override
  public String forType() {
    return BoardDisplayScreen.FACE_EVENT_TYPE;
  }

  @Override
  public String forKey() {
    return "changeFace";
  }

  @Override
  public void receive(Event event) {
    Facing facing = Facing.class.cast(event.properties.get(BoardDisplayScreen.FACING_EVENT_PROPERTY));
    Board board = this.engineHeart.board;
    board.facing = facing;
  }
  
}
