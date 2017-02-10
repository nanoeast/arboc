package nanoeast.snake.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatch {
  
  public EventHeap heap;
  public Map<String, List<EventHandler>> handlersPerType;

  public EventDispatch() {
    this.heap = new EventHeap();
    this.handlersPerType = new HashMap<String, List<EventHandler>>();
  }
  
  private void ensureHandlerStorageForType(String type) {
    if (this.handlersPerType.get(type) == null) {
      this.handlersPerType.put(type, new ArrayList<EventHandler>());
    }
  }
  
  public void addHandler(EventHandler handler) {
    String type = handler.forType();
    this.ensureHandlerStorageForType(type);
    this.handlersPerType.get(type).add(handler);
  }
  
  public void removeHandler(EventHandler handler) {
    String type = handler.forType();
    this.ensureHandlerStorageForType(type);
    this.handlersPerType.get(type).remove(handler);
  }
  
  public void enqueue(String type, long systemMillis, Map<String, Object> properties) {
    this.heap.push(type, properties, systemMillis);
  }
  
  
  
  public void dispatch(long systemMillisThreshold) {
    List<Event> events = this.heap.takeUntil(systemMillisThreshold);
    for (Event event : events) {
      List<EventHandler> handlers = this.handlersPerType.get(event.type);
      for (EventHandler handler : handlers) {
        handler.receive(event); // TODO: Add veto capability?
      }
    }
  }

}
