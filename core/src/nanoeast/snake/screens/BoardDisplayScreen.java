package nanoeast.snake.screens;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.logic.Board;
import nanoeast.snake.logic.CoordinateUtils;
import nanoeast.snake.logic.Pair;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BoardDisplayScreen extends ScreenAdapter {

    private static final String ASSET_PATH_TAIL = "images/squareyellow.png";
    private static final String ASSET_PATH_HEAD = "images/squarered.png";
    private static final String ASSET_PATH_TARGET = "images/squareblue.png";
    public EngineHeart engineHeart;
    public SpriteBatch spriteBatch;
    private Texture targetTexture, headTexture, tailTexture;
    private TextureRegion targetTextureRegion, headTextureRegion, tailTextureRegion;
    private boolean texturesLoaded;
    public Camera camera;
    private int cellSize, topPadding, leftPadding;
    
    private InputProcessor inputProcessor;
  
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
      
      this.inputProcessor = new BasicInput(this.engineHeart.board);
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
      
      doOutput();
    }
    
    
    
    @Override
    public void show() {
      super.show();
      this.engineHeart.inputMultiplexer.addProcessor(this.inputProcessor);
    }
    
    @Override
    public void dispose() {
    // TODO Auto-generated method stub
    super.dispose();
    this.engineHeart.inputMultiplexer.removeProcessor(this.inputProcessor);
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
      System.out.println("snake: " + this.engineHeart.board.snake.toString());
      for (int i = 0; i < this.engineHeart.board.snake.size(); i++) {
        Integer cellIndex = this.engineHeart.board.snake.get(i);
        System.out.println("cellindex: " + cellIndex + " i: " + i);
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
  Board board;
  
  public BasicInput(Board board) {
    this.board = board;
  }
  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    System.out.println("keycode: " + keycode);
    board.nextHead(board.hasNextHead());
    return true;
  }
}