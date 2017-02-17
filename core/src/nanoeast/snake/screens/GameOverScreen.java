package nanoeast.snake.screens;

import nanoeast.snake.EngineHeart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen extends ScreenAdapter {

  SpriteBatch batch;
  Texture img;
  EngineHeart engineHeart;
  
  public GameOverScreen(EngineHeart engineHeart) {
    batch = new SpriteBatch();
    img = new Texture("badlogic.jpg");
  }
  
  @Override
  public void render(float delta) {
    super.render(delta);
    Gdx.gl.glClearColor(0, 0, 0, 1); // clear to black
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    batch.draw(img, 0, 0);
    batch.end();
  }
  
  @Override
  public void dispose() {
    batch.dispose();
    img.dispose();
    super.dispose();
  }

}
