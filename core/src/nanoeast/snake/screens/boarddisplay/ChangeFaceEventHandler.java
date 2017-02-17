package nanoeast.snake.screens.boarddisplay;

import java.util.List;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.CoordinateUtils;
import nanoeast.snake.logic.Facing;
import nanoeast.snake.logic.Pair;
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
    if (facing != null) {
      Board board = this.engineHeart.board;
      List<Integer> snake = board.snake;
      boolean invalid = false;
      if (snake.size() > 1) {
        Integer head = snake.get(0);
        Integer neck = snake.get(1);
        Pair<Integer, Integer> nowPair = this.engineHeart.pairPool.obtain();
        Pair<Integer, Integer> thenPair = this.engineHeart.pairPool.obtain();
        CoordinateUtils.setCoordinatesFromCellIndex(board.width, head, nowPair);
        CoordinateUtils.setCoordinatesFromCellIndex(board.width, neck, thenPair);
        invalid = facing.isInvalid(thenPair, nowPair);
        this.engineHeart.pairPool.free(nowPair);
        this.engineHeart.pairPool.free(thenPair);
      }
      if (!invalid) {
        board.facing = facing;
      }
    }
  }
  
}
