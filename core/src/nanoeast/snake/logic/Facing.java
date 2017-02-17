package nanoeast.snake.logic;

public enum Facing {
  UP {

    @Override
    public void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      nowPair.item1 = thenPair.item1;
      nowPair.item2 = thenPair.item2 - 1;
    }

    @Override
    public boolean isInvalid(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      return nowPair.item1 == thenPair.item1 && nowPair.item2 == thenPair.item2 + 1;
    }
  },
  DOWN {

    @Override
    public void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      nowPair.item1 = thenPair.item1;
      nowPair.item2 = thenPair.item2 + 1;
    }

    @Override
    public boolean isInvalid(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      return nowPair.item1 == thenPair.item1 && nowPair.item2 == thenPair.item2 - 1;
    }
  },
  LEFT {

    @Override
    public void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      nowPair.item1 = thenPair.item1 - 1;
      nowPair.item2 = thenPair.item2;
    }

    @Override
    public boolean isInvalid(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      return nowPair.item1 == thenPair.item1 + 1 && nowPair.item2 == thenPair.item2;
    }

  },
  RIGHT {
    @Override
    public void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      nowPair.item1 = thenPair.item1 + 1;
      nowPair.item2 = thenPair.item2;
    }

    @Override
    public boolean isInvalid(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
      return nowPair.item1 == thenPair.item1 - 1 && nowPair.item2 == thenPair.item2;
    }

  };

  public abstract void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair);

  public abstract boolean isInvalid(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair);
}
