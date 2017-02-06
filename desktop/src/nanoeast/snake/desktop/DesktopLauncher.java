package nanoeast.snake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import nanoeast.snake.Default;
import nanoeast.snake.EngineHeart;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new Default(), config);
		new LwjglApplication(new EngineHeart(), config);
	}
}
