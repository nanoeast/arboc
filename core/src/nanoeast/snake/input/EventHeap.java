package nanoeast.snake.input;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.badlogic.gdx.utils.Pool;

public class EventHeap<InfoType> {
  
  private final PriorityQueue<Event<InfoType>> heap;
  private final Pool<Event<InfoType>> pool;
  
  private final class EventComparator<T extends InfoType> implements Comparator<Event<? extends InfoType>> {
    @Override
    public int compare(Event<? extends InfoType> o1, Event<? extends InfoType> o2) {
      long lhs = o1.systemMillis, rhs = o2.systemMillis;
      return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }
  }
  private final class EventPool extends Pool<Event<InfoType>> {
    @Override
    protected Event<InfoType> newObject() {
      return new Event<InfoType>(null, 0);
    }
  }
  
  public EventHeap() {
    EventComparator<InfoType> comparator = new EventComparator<>();
    this.heap = new PriorityQueue<Event<InfoType>>(1, comparator);
    this.pool = new EventPool();
  }
  
  public void push(InfoType eventInfo, long systemMillis) {
    Event<InfoType> event = this.pool.obtain();
    event.eventInfo = eventInfo;
    event.systemMillis = systemMillis;
    this.heap.offer(event);
  }
  
  public Event<InfoType> pop() {
    return this.heap.poll();
  }
  
}