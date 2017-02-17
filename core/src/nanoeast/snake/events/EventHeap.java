package nanoeast.snake.events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;

import com.badlogic.gdx.utils.Pool;

public class EventHeap {
  
  public static final Comparator HEAP_COMPARATOR = new Comparator<Event>() {
    @Override
    public int compare(Event o1, Event o2) {
      long lhs = o1.systemMillis, rhs = o2.systemMillis;
      return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }
  };
  
  public final PriorityQueue<Event> heap;
  public final Pool<Event> pool;
  
  private final class EventPool extends Pool<Event> {
    @Override
    protected Event newObject() {
      return new Event();
    }
  }
  
  public EventHeap() {
    this.heap = new PriorityQueue<Event>(HEAP_COMPARATOR);
    this.pool = new EventPool();
  }
  
  public void push(String type, Map<String, Object> properties, long systemMillis) {
    Event event = this.pool.obtain();
    event.properties = properties;
    event.type = type;
    event.systemMillis = systemMillis;
    this.heap.offer(event);
  }
  public void takeUntil(long systemMillisThreshold, Consumer<Event> eventConsumer) {
    List<Event> events = new ArrayList<Event>();
    boolean hasMoreEvents = true;
    while (hasMoreEvents) {
      if (!this.heap.isEmpty() && this.heap.peek().systemMillis <= systemMillisThreshold) {
        events.add(this.heap.poll());
      }  else {
        hasMoreEvents = false;
      }
    }
    for (Event event : events) {
      eventConsumer.accept(event);
      this.pool.free(event);
    }
  }
  
}