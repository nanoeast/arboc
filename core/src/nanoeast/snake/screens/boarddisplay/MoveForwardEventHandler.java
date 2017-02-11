package nanoeast.snake.screens.boarddisplay;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.Board;
import nanoeast.snake.screens.BoardDisplayScreen;

public class MoveForwardEventHandler implements EventHandler {
  EngineHeart engineHeart;

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
    board.nextHead(cellIndex);
  }
  
}