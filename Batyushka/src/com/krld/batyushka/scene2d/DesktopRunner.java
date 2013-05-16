package com.krld.batyushka.scene2d;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopRunner {
	public static void main(String[] args) {
		new LwjglApplication(new Engine(), "Батюшка", Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT, false);
	}
}
