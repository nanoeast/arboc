package nanoeast.snake.screens.boarddisplay;

import java.util.Collections;
import java.util.Map;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.Board;
import nanoeast.snake.screens.BoardDisplayScreen;

import com.badlogic.gdx.Gdx;

public class MoveForwardEventHandler implements EventHandler {
  EngineHeart engineHeart;
  
  public static final Map<String, Object> PROPERTIES = Collections.<String, Object>emptyMap();

  public MoveForwardEventHandler(EngineHeart engineHeart) {
    this.engineHeart = engineHeart;
  }

  @Override
  public String forType() {
    return BoardDisplayScreen.MOVE_EVENT_TYPE;
  }

  @Override
  public String forKey() {
    return "moveSnake";
  }

  @Override
  public void receive(Event event) {
    Board board = this.engineHeart.board;
    int cellIndex = board.hasNextHead();
    if (cellIndex == Board.INVALID_CELL_INDEX) {
      long systemMillis = Gdx.input.getCurrentEventTime();
      this.engineHeart.eventDispatch.enqueue(BoardDisplayScreen.GAME_OVER_EVENT_TYPE, systemMillis,
          PROPERTIES);
    } else {
      board.nextHead(cellIndex);
    }
  }

}
