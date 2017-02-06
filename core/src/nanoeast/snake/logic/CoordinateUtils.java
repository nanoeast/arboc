package nanoeast.snake.logic;

public final class CoordinateUtils {

  private CoordinateUtils() {
  }
  
  public static final int getCellIndexFromCoordinates(int width, int x, int y) {
    return (width * y) + x;
  }
  public static final void setCoordinatesFromCellIndex(int width, int cellIndex, Pair<Integer, Integer> coord) {
      coord.item2 = cellIndex / width;
      coord.item1 = cellIndex % width;
  }

}
