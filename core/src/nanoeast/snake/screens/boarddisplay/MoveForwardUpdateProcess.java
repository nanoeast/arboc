package nanoeast.snake.screens.boarddisplay;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.screens.BoardDisplayScreen;
import nanoeast.snake.triggers.UpdateProcess;

import com.badlogic.gdx.Gdx;

public class MoveForwardUpdateProcess implements UpdateProcess {
  
  EngineHeart engineHeart;
  long millisPerAdvance, millisInBank;
  
  
  
  public MoveForwardUpdateProcess(EngineHeart engineHeart, long millisPerAdvance) {
    super();
    this.engineHeart = engineHeart;
    this.millisPerAdvance = millisPerAdvance;
    this.millisInBank = 0;
  }

  @Override
  public void update(long differenceInMillis) {
    this.millisInBank += differenceInMillis;
    long currentTime = Gdx.input.getCurrentEventTime();
    long previousTime = currentTime - differenceInMillis;
    long frameTime = previousTime;
    while (this.millisInBank >= this.millisPerAdvance) {
      frameTime += this.millisPerAdvance;
      this.engineHeart.eventDispatch.enqueue(BoardDisplayScreen.MOVE_EVENT_TYPE, frameTime, null);
      this.millisInBank -= this.millisPerAdvance;
    }
  }
}
