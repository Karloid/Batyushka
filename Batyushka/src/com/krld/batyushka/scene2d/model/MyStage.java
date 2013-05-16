package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.krld.batyushka.scene2d.Engine;
import com.krld.batyushka.scene2d.model.tiles.GrassFlowersTile;
import com.krld.batyushka.scene2d.model.tiles.GrassTile;

public class MyStage extends Stage {
    private static final int SIZE = 100;
    private static final short TILE_SIZE = 64;
    private final byte[][] tileMap = new byte[SIZE][SIZE];
    private final Player player;

    public MyStage(int windowWidth, int windowHeight, boolean b, SpriteBatch batch) {
        super(windowWidth, windowHeight, b, batch);
        /**
         * Создаем главную текстуру
         */
        Texture textureAtlas = new Texture(Gdx.files.internal("batyushka/res/character.png"));
        /**
         * Получаем из нее необходимую нам часть
         */
        TextureRegion characterTexture = new TextureRegion(textureAtlas, 0, 0, 32, 32);

        for (int x = 0; x < MyStage.SIZE; x++) {
            for (int y = 0; y < MyStage.SIZE; y++) {
                tileMap[x][y] = (byte) (Math.random() * 2);
                addTile(tileMap[x][y], x, y);
            }
        }
        player = new Player(0, 0, characterTexture);
        addActor(player);
        setKeyboardFocus(player);
    }

    private void addTile(byte b, int x, int y) {
        if (b == 0) {
            addActor(new GrassTile(x * TILE_SIZE, y * TILE_SIZE));
        } else if (b == 1) {
            addActor(new GrassFlowersTile(x * TILE_SIZE, y * TILE_SIZE));
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        player.touchDown(x - (Engine.WINDOW_WIDTH / 2), (y - (Engine.WINDOW_HEIGHT / 2)), button);
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        player.touchDragged(x - (Engine.WINDOW_WIDTH / 2), (y - (Engine.WINDOW_HEIGHT / 2)), pointer);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        player.touchUp(x, y, button);
        return true;
    }
}
