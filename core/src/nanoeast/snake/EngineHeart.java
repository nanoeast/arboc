package nanoeast.snake;

import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.Facing;
import nanoeast.snake.screens.BoardDisplayScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class EngineHeart extends Game {
    
    Screen currentScreen;
    public AssetManager assetManager;
    public Board board;
    public int width, height;
    
    public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT = 50;

    public EngineHeart() {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public EngineHeart(int boardWidth, int boardHeight) {
      this.assetManager = new AssetManager();
      this.board = new Board(boardWidth, boardHeight);
      this.board.initializeSnake(0, 0, 4, Facing.RIGHT);
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub
        this.currentScreen = new BoardDisplayScreen(this);
        this.setScreen(this.currentScreen);
        //this.currentScreen.show();
    }

}
