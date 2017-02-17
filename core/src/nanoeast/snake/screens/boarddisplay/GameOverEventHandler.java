package nanoeast.snake.screens.boarddisplay;

import java.util.List;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.CoordinateUtils;
import nanoeast.snake.logic.Pair;
import nanoeast.snake.screens.BoardDisplayScreen;
import nanoeast.snake.screens.GameOverScreen;
import nanoeast.snake.triggers.UpdateProcess;


public class GameOverEventHandler implements EventHandler {
  
  EngineHeart engineHeart;

  public GameOverEventHandler(EngineHeart engineHeart) {
    super();
    this.engineHeart = engineHeart;
  }

  @Override
  public String forType() {
    return BoardDisplayScreen.GAME_OVER_EVENT_TYPE;
  }

  @Override
  public String forKey() {
    return this.getClass().getName();
  }

  @Override
  public void receive(Event event) {
    this.engineHeart.setScreen(new GameOverScreen(this.engineHeart));
  }
  
}