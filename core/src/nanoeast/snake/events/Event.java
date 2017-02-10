package nanoeast.snake.events;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Pool.Poolable;

public class Event implements Poolable{

  public String type;
  public Map<String, Object> properties;
  public long systemMillis;
  
  public Event() {
    this(null, null, 0);
  }
  
  public Event(String type, Map<String, Object> properties, long systemMillis) {
    this.type = type;
    this.properties = properties;
    this.systemMillis = systemMillis;
  }
  
  @Override
  public void reset() {
    this.systemMillis = 0;
    this.properties = null;
    this.type = null;
  }

}
