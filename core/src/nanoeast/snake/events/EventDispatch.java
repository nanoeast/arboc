package nanoeast.snake.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatch {
  
  public EventHeap heap;
  public Map<String, Map<String, EventHandler>> handlersPerType;

  public EventDispatch() {
    this.heap = new EventHeap();
    this.handlersPerType = new HashMap<String, Map<String, EventHandler>>();
  }
  
  private void ensureHandlerStorageForType(String type) {
    if (this.handlersPerType.get(type) == null) {
      this.handlersPerType.put(type, new HashMap<String, EventHandler>());
    }
  }
  
  public void addHandler(EventHandler handler) {
    String type = handler.forType();
    String key = handler.forKey();
    this.ensureHandlerStorageForType(type);
    this.handlersPerType.get(type).put(key, handler);
  }
  
  public void removeHandler(EventHandler handler) {
    String type = handler.forType();
    String key = handler.forKey();
    this.ensureHandlerStorageForType(type);
    this.handlersPerType.get(type).remove(key);
  }
  
  public void enqueue(String type, long systemMillis, Map<String, Object> properties) {
    this.heap.push(type, properties, systemMillis);
  }
  
  public void dispatch(long systemMillisThreshold) {
    this.heap.takeUntil(systemMillisThreshold, (Event event) -> {
      Map<String, EventHandler> handlers = this.handlersPerType.get(event.type);
      Map<String, EventHandler> handlersCopy = new HashMap<String, EventHandler>(handlers);
      for (EventHandler handler : handlersCopy.values()) {
        handler.receive(event); // TODO: Add veto capability?
      }      
    });
  }
}
