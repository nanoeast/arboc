package nanoeast.snake.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.events.Event;
import nanoeast.snake.events.EventHandler;
import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.CoordinateUtils;
import nanoeast.snake.logic.Facing;
import nanoeast.snake.logic.Pair;
import nanoeast.snake.screens.boarddisplay.ChangeFaceEventHandler;
import nanoeast.snake.screens.boarddisplay.MoveForwardEventHandler;
import nanoeast.snake.screens.boarddisplay.MoveForwardUpdateProcess;
import nanoeast.snake.triggers.UpdateProcess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class BoardDisplayScreen extends ScreenAdapter {

    private static final String ASSET_PATH_TAIL = "images/squareyellow.png";
    private static final String ASSET_PATH_HEAD = "images/squarered.png";
    private static final String ASSET_PATH_TARGET = "images/squareblue.png";

    public static final Map<String, Object> PROPERTIES_FACE_UP = new HashMap<String, Object>();
    public static final Map<String, Object> PROPERTIES_FACE_DOWN = new HashMap<String, Object>();
    public static final Map<String, Object> PROPERTIES_FACE_LEFT = new HashMap<String, Object>();
    public static final Map<String, Object> PROPERTIES_FACE_RIGHT = new HashMap<String, Object>();

    public static final String FACING_EVENT_PROPERTY = "facing";
    public static final String FACE_EVENT_TYPE = "faceEvent";
    public static final String MOVE_EVENT_TYPE = "moveEvent";
    
    static {
      PROPERTIES_FACE_UP.put(BoardDisplayScreen.FACING_EVENT_PROPERTY, Facing.UP);
      PROPERTIES_FACE_DOWN.put(BoardDisplayScreen.FACING_EVENT_PROPERTY, Facing.DOWN);
      PROPERTIES_FACE_LEFT.put(BoardDisplayScreen.FACING_EVENT_PROPERTY, Facing.LEFT);
      PROPERTIES_FACE_RIGHT.put(BoardDisplayScreen.FACING_EVENT_PROPERTY, Facing.RIGHT);
    }
    
    public EngineHeart engineHeart;
    public SpriteBatch spriteBatch;
    private Texture targetTexture, headTexture, tailTexture;
    private TextureRegion targetTextureRegion, headTextureRegion, tailTextureRegion;
    private boolean texturesLoaded;
    public Camera camera;
    private int cellSize, topPadding, leftPadding;
    
    private InputProcessor inputProcessor;
    private List<EventHandler> gameEventHandlers;
    private List<UpdateProcess> gameUpdateProcesses;
  
    public BoardDisplayScreen(EngineHeart engineHeart) {
      this.engineHeart = engineHeart;
      this.engineHeart.assetManager.load(BoardDisplayScreen.ASSET_PATH_TARGET, Texture.class);
      this.engineHeart.assetManager.load(BoardDisplayScreen.ASSET_PATH_HEAD, Texture.class);
      this.engineHeart.assetManager.load(BoardDisplayScreen.ASSET_PATH_TAIL, Texture.class);
      this.texturesLoaded = false;
      
      int cellsAcross = this.engineHeart.board.width + 2;
      int cellsDown = this.engineHeart.board.height + 2;
      int graphicsWidth = Gdx.graphics.getWidth();
      int graphicsHeight = Gdx.graphics.getHeight();
      
      int cellSizeMinAcross = graphicsWidth / cellsAcross;
      int cellSizeMinDown = graphicsHeight / cellsDown;
      
      this.cellSize = Math.min(cellSizeMinAcross, cellSizeMinDown);
      
      this.topPadding = (graphicsHeight - (cellsDown * this.cellSize)) / 2;
      this.leftPadding = (graphicsWidth - (cellsAcross * this.cellSize)) / 2;
      
      boolean useYDownCoordinateSystem = true;
      OrthographicCamera orthographicCamera = new OrthographicCamera(graphicsWidth, graphicsHeight);
      orthographicCamera.setToOrtho(useYDownCoordinateSystem);
      this.camera = orthographicCamera;
      float cameraX = this.camera.viewportWidth / 2.0f;
      float cameraY = this.camera.viewportHeight / 2.0f;
      float cameraZ = 0.0f;
      this.camera.position.set(cameraX, cameraY, cameraZ); // center the camera
      this.camera.update();
      
      this.spriteBatch = new SpriteBatch();
      
      this.inputProcessor = new BasicInput(this.engineHeart);
      this.gameEventHandlers = new ArrayList<EventHandler>();
      this.gameEventHandlers.add(new MoveForwardEventHandler(this.engineHeart));
      this.gameEventHandlers.add(new ChangeFaceEventHandler(this.engineHeart));
      
      this.gameUpdateProcesses = new ArrayList<UpdateProcess>();
      this.gameUpdateProcesses.add(new MoveForwardUpdateProcess(this.engineHeart, 50));
    }
    
    @Override
    public void render(float delta) {
      super.render(delta);
      if (!this.engineHeart.assetManager.update()) {
        return;
      }
      if (!this.texturesLoaded) {
        this.ensureTextures();
        return;
      }
      
      // advance all running update processes
      
      long differenceInMillis = Math.round(delta * 1000d);
      for (UpdateProcess process : this.gameUpdateProcesses) {
        process.update(differenceInMillis);
      }
      
      // process all events when we can.
      long currentTime = TimeUtils.nanoTime();
      this.engineHeart.eventDispatch.dispatch(currentTime);
      
      doOutput();
    }
    
    @Override
    public void show() {
      super.show();
      this.engineHeart.inputMultiplexer.addProcessor(this.inputProcessor);
      for (EventHandler handler : this.gameEventHandlers) {
        this.engineHeart.eventDispatch.addHandler(handler);
      }
      
      
    }
    
    @Override
    public void dispose() {
      this.engineHeart.inputMultiplexer.removeProcessor(this.inputProcessor);
      for (EventHandler handler : this.gameEventHandlers) {
        this.engineHeart.eventDispatch.removeHandler(handler);
      }
      super.dispose();
    }
    
    
    private void doOutput() {
      Gdx.gl.glClearColor(0, 0, 0, 1); // clear to black
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      this.camera.update();
      this.spriteBatch.setProjectionMatrix(this.camera.combined);
      this.spriteBatch.begin();
      this.renderBoard(this.engineHeart.board);
      this.spriteBatch.end();
    }

    private void renderBoard(Board board) {
      Pair<Integer, Integer> cellCoordinates = new Pair<>(0, 0);
      for (int i = 0; i < this.engineHeart.board.snake.size(); i++) {
        Integer cellIndex = this.engineHeart.board.snake.get(i);
        CoordinateUtils.setCoordinatesFromCellIndex(this.engineHeart.board.width, cellIndex, cellCoordinates);
        TextureRegion snakeTextureRegion = (i == 0) ? this.headTextureRegion : this.tailTextureRegion;
        int cellPositionX = this.leftPadding + (this.cellSize * cellCoordinates.item1);
        int cellPositionY = this.topPadding + (this.cellSize * cellCoordinates.item2);
        this.spriteBatch.draw(snakeTextureRegion, cellPositionX, cellPositionY, this.cellSize, this.cellSize);
      }  
    }

    private void ensureTextures() {
      if (this.targetTexture == null) {
        if (this.engineHeart.assetManager.isLoaded(ASSET_PATH_TARGET)) {
          this.targetTexture = this.engineHeart.assetManager.get(ASSET_PATH_TARGET, Texture.class);
          this.targetTextureRegion = new TextureRegion(targetTexture);
        }  else {
          return;
        }
      }
      if (this.headTexture == null) {
        if (this.engineHeart.assetManager.isLoaded(ASSET_PATH_HEAD)) {
          this.headTexture = this.engineHeart.assetManager.get(ASSET_PATH_HEAD, Texture.class);
          this.headTextureRegion = new TextureRegion(headTexture);
        }  else {
          return;
        }
      }
      if (this.tailTexture == null) {
        if (this.engineHeart.assetManager.isLoaded(ASSET_PATH_TAIL)) {
          this.tailTexture = this.engineHeart.assetManager.get(ASSET_PATH_TAIL, Texture.class);
          this.tailTextureRegion = new TextureRegion(tailTexture);
        }  else {
          return;
        }
      }
      this.texturesLoaded = true;
    }

}

class BasicInput extends InputAdapter {
  EngineHeart engineHeart;
  
  public BasicInput(EngineHeart engineHeart) {
    this.engineHeart = engineHeart;
  }
  @Override
  public boolean keyDown(int keycode) {
    long systemMillis = Gdx.input.getCurrentEventTime();
    Map<String, Object> properties = new HashMap<String, Object>();
    switch (keycode) {
      case Keys.UP : { properties = BoardDisplayScreen.PROPERTIES_FACE_UP; break; } 
      case Keys.DOWN : { properties = BoardDisplayScreen.PROPERTIES_FACE_DOWN; break; }
      case Keys.LEFT : { properties = BoardDisplayScreen.PROPERTIES_FACE_LEFT; break; }
      case Keys.RIGHT : { properties = BoardDisplayScreen.PROPERTIES_FACE_RIGHT; break; }
    }
    this.engineHeart.eventDispatch.enqueue(BoardDisplayScreen.FACE_EVENT_TYPE, systemMillis, properties);
    return true;
  }
}

class BasicEventHandler implements EventHandler {
  
  EngineHeart engineHeart;
  public BasicEventHandler(EngineHeart engineHeart) {
    this.engineHeart = engineHeart;
  }

  @Override
  public String forKey() {
    return "basic";
  }
  @Override
  public String forType() {
    return BoardDisplayScreen.FACE_EVENT_TYPE;
  }

  @Override
  public void receive(Event event) {
    Facing facing = Facing.class.cast(event.properties.get(BoardDisplayScreen.FACING_EVENT_PROPERTY));
    Board board = this.engineHeart.board;
    board.facing = facing;
    int head = board.hasNextHead();
    if (head != Board.INVALID_CELL_INDEX) {
      board.nextHead(head);
    }
  }
  
}




