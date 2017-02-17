package nanoeast.snake.logic;

import java.util.ArrayList;
import java.util.List;

public class Board {

  public static final int INVALID_CELL_INDEX = -1;

  public int width, height;
  public List<Integer> snake;
  public Facing facing;
  public int length;

  public Board(int width, int height) {
    checkMinimumWidthAndHeight(width, height);
    this.width = width;
    this.height = height;
    this.clearSnake();
  }

  public void clearBoard() {
    this.clearSnake();
  }

  private void clearSnake() {
    if (this.snake == null) {
      this.snake = new ArrayList<>();
    }
    this.snake.clear();
  }

  private void checkMinimumWidthAndHeight(int width, int height) {
    if (width < 2) {
      throw new IllegalArgumentException("Insufficient width of " + width);
    }
    if (height < 2) {
      throw new IllegalArgumentException("Insufficient height of " + height);
    }
  }

  public void createSnake(int x, int y, int length, Facing facing) {
    if (length < 2) {
      throw new IllegalArgumentException("Insufficient snake length");
    }
    this.facing = facing;
    this.length = length;
    Pair<Integer, Integer> origin = new Pair<>(x, y);
    for (int i = 0; i < length; i++) {
      this.snake.add(0,
          CoordinateUtils.getCellIndexFromCoordinates(this.width, origin.item1, origin.item2));
      facing.setNext(origin, origin);
    }
  }


  /**
   * Returns the next cell index (from 0 to (width * height) - 1) if there is a valid next move.
   * Returns {@link #INVALID_CELL_INDEX} if the next move is out of bounds.
   */
  public int hasNextHead() {
    Pair<Integer, Integer> previous = new Pair<>(0, 0);
    Pair<Integer, Integer> next = new Pair<>(0, 0);
    Integer head = this.snake.iterator().next();
    CoordinateUtils.setCoordinatesFromCellIndex(this.width, head, previous);
    this.facing.setNext(previous, next);
    if (next.item1 < 0 || next.item1 >= this.width) {
      return INVALID_CELL_INDEX;
    }
    if (next.item2 < 0 || next.item2 >= this.height) {
      return INVALID_CELL_INDEX;
    }
    return CoordinateUtils.getCellIndexFromCoordinates(this.width, next.item1, next.item2);
  }

  /**
   * Sets the next head of the snake. Populate by invoking
   * 
   * <pre>
   * {@code
   *  nextHead(hasNextHead());
   * }
   * </pre>
   */
  public void nextHead(int cellIndex) {
    this.snake.add(0, cellIndex);
    this.snake.remove(this.snake.size() - 1);
  }


}
