package nanoeast.snake;

import nanoeast.snake.events.EventDispatch;
import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.Facing;
import nanoeast.snake.logic.Pair;
import nanoeast.snake.screens.BoardDisplayScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Pool;

public class EngineHeart extends Game {
    
    public AssetManager assetManager;
    public InputMultiplexer inputMultiplexer;
    public EventDispatch eventDispatch;
    public Pool<Pair<Integer, Integer>> pairPool = new Pool<Pair<Integer, Integer>>() {
      @Override
      protected Pair<Integer, Integer> newObject() {
        return new Pair<>(-1, -1);
      }
    };
    public Board board;
    public int width, height;
    
    public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;

    public EngineHeart() {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public EngineHeart(int boardWidth, int boardHeight) {
      this.assetManager = new AssetManager();
      this.inputMultiplexer = new InputMultiplexer();
      this.eventDispatch = new EventDispatch();
      
      this.board = new Board(boardWidth, boardHeight);
      this.board.createSnake(0, 0, 4, Facing.RIGHT);
      
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        Screen currentScreen = new BoardDisplayScreen(this);
        this.setScreen(currentScreen);
    }

}
