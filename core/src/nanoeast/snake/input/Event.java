package nanoeast.snake.input;

import com.badlogic.gdx.utils.Pool.Poolable;

public class Event<InfoType> implements Poolable{
  
  InfoType eventInfo;
  long systemMillis;

  public Event(InfoType eventInfo, long systemMillis) {
    this.eventInfo = eventInfo;
    this.systemMillis = systemMillis;
  }

  @Override
  public void reset() {
    this.eventInfo = null;
    this.systemMillis = 0;
  }

}
