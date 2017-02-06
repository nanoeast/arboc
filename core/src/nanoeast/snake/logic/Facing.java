package nanoeast.snake.logic;

public enum Facing {
    UP {

        @Override
        void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
            nowPair.item1 = thenPair.item1;
            nowPair.item2 = thenPair.item2 - 1;
        }
    }
    ,
    DOWN {

        @Override
        void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
            nowPair.item1 = thenPair.item1;
            nowPair.item2 = thenPair.item2 + 1;
        }
    }
    ,
    LEFT {

        @Override
        void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
            nowPair.item1 = thenPair.item1 - 1;
            nowPair.item2 = thenPair.item2;
        }
        
    }
    ,
    RIGHT {

        @Override
        void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair) {
            nowPair.item1 = thenPair.item1 + 1;
            nowPair.item2 = thenPair.item2;
        }
        
    }
    ;
    
    abstract void setNext(Pair<Integer, Integer> thenPair, Pair<Integer, Integer> nowPair);
}
