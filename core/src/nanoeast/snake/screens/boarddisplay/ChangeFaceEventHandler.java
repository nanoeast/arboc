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

import com.badlogic.gdx.utils.Pool;

public class ChangeFaceEventHandler implements EventHandler {
  
  EngineHeart engineHeart;
  Pool<Pair<Integer, Integer>> pairPool = new Pool<Pair<Integer,Integer>>() {
    protected nanoeast.snake.logic.Pair<Integer,Integer> newObject() {
      return new Pair<Integer, Integer>(0, 0);
    }
  };
  
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
        Pair<Integer, Integer> nowPair = this.pairPool.obtain();
        Pair<Integer, Integer> thenPair = this.pairPool.obtain();
        CoordinateUtils.setCoordinatesFromCellIndex(board.width, head, nowPair);
        CoordinateUtils.setCoordinatesFromCellIndex(board.width, neck, thenPair);
        invalid = facing.isInvalid(thenPair, nowPair);
        this.pairPool.free(nowPair);
        this.pairPool.free(thenPair);
      }
      if (!invalid) {
        board.facing = facing;
      }
    }
  }
  
}
