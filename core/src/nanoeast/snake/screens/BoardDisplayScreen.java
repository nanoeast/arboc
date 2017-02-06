package nanoeast.snake.screens;

import nanoeast.snake.EngineHeart;
import nanoeast.snake.logic.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
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
      
      //boolean useYDownCoordinateSystem = true;
      this.camera = new OrthographicCamera(graphicsWidth, graphicsHeight);
      float cameraX = this.camera.viewportWidth / 2.0f;
      float cameraY = this.camera.viewportHeight / 2.0f;
      float cameraZ = 0.0f;
      this.camera.position.set(cameraX, cameraY, cameraZ); // center the camera
      this.camera.update();
      
      this.spriteBatch = new SpriteBatch();
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
      
      this.camera.update();
      this.spriteBatch.setProjectionMatrix(this.camera.combined);
      
      this.spriteBatch.begin();
      this.renderBoard(this.engineHeart.board);
      this.spriteBatch.end();
    }

    private void renderBoard(Board board) {
      for (int row = 0; row < board.height; row++) {
        for (int col = 0; col < board.width; col++) {
          int cellPositionX = this.leftPadding + ((col + 1) * this.cellSize);
          int cellPositionY = this.topPadding + ((row + 1) * this.cellSize);
          this.spriteBatch.draw(this.headTextureRegion, cellPositionX, cellPositionY, this.cellSize, this.cellSize);
        }
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
