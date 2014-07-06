package batyushka;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopRunner {
	public static void main(String[] args) {
		new LwjglApplication(new Engine(), "Батюшка: Путь к свету", Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT, false);
	}
}
