package nanoeast.snake.logic;

import java.util.Set;
import java.util.TreeSet;

public class Board {
    
    public int width, height;
    public int[][] cellsAsArray;
    public Set<Integer> cellsAsSet;
    public Facing facing;
    public int length;

    public Board(int width, int height) {
        checkMinimumWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        this.initializeArrayFromWidthAndHeight();
    }
    
    public void clearBoard() {
      this.initializeArrayFromWidthAndHeight();
      for (int[] row : this.cellsAsArray) {
        for (int i = 0; i < row.length; i++) {
          row[i] = 0;
        }
      }
      this.initializeCellsAsSet();
      this.cellsAsSet.clear();
    }
    
    private void initializeCellsAsSet() {
      if (this.cellsAsSet == null) {
        this.cellsAsSet = new TreeSet<>();
      }
    }

    private void checkMinimumWidthAndHeight(int width, int height) {
        if (width < 4) {
            throw new IllegalArgumentException("Insufficient width of " + width);
        }
        if (height < 1) {
            throw new IllegalArgumentException("Insufficient height of " + height);
        }
    }

    private void initializeArrayFromWidthAndHeight() {
        this.cellsAsArray = new int[this.height][];
        for (int i = 0; i < this.height; i++) {
          this.cellsAsArray[i] = new int[this.width];
        };
    }
    
    public void initializeSnake(int x, int y, int length, Facing facing) {
      this.facing = facing;
      this.length = length;
      Pair<Integer, Integer> origin = new Pair<>(x, y);
      for (int i = 0; i < length; i++) {
        int segment = i + 1;
        this.cellsAsArray[origin.item1][origin.item2] = segment;
        this.cellsAsSet.add(this.getCoord(origin.item1, origin.item2));
        facing.setNext(origin, origin);
      }
    }
    
    private int getCoord(int x, int y) {
      return (this.width * y) + x;
    }
    
    
    
}
