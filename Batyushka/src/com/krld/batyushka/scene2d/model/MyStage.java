package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.krld.batyushka.scene2d.Engine;
import com.krld.batyushka.scene2d.model.tiles.GrassFlowersTile;
import com.krld.batyushka.scene2d.model.tiles.GrassTile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyStage extends Stage {
    private static final int SIZE = 100;
    private static final short TILE_SIZE = 64;
    private final byte[][] tileMap = new byte[SIZE][SIZE];
    private final Player player;
    private List<FireBall> fireBalls;
    private List<MyUnit> units;

    public MyStage(int windowWidth, int windowHeight, boolean b, SpriteBatch batch) {
        super(windowWidth, windowHeight, b, batch);
        Texture textureAtlas = new Texture(Gdx.files.internal("batyushka/res/character.png"));
        TextureRegion characterTexture = new TextureRegion(textureAtlas, 0, 0, 32, 32);
        for (int x = 0; x < MyStage.SIZE; x++) {
            for (int y = 0; y < MyStage.SIZE; y++) {
                tileMap[x][y] = (byte) (Math.random() * 2);
                addTile(tileMap[x][y], x, y);
            }
        }
        setFireBalls(new ArrayList<FireBall>());
        units =  new ArrayList<MyUnit>();
        player = new Player(50 * TILE_SIZE, 50 * TILE_SIZE, characterTexture);
        addActor(player);
        Wolf wolf = new Wolf(53 * TILE_SIZE, 53 * TILE_SIZE);
        units.add(wolf);
        addActor(wolf);
     //   wolf.action(new FadeOut().);
        setKeyboardFocus(player);
    //    world.
    }

    private void addTile(byte b, int x, int y) {
        if (b == 0) {
            addActor(new GrassTile(x * TILE_SIZE, y * TILE_SIZE));
        } else if (b == 1) {
            addActor(new GrassFlowersTile(x * TILE_SIZE, y * TILE_SIZE));
        }
    }

    @Override
    public void draw() {
        checkFireballCollisions();
        super.draw();
    }

    private void checkFireballCollisions() {
        Set<FireBall> setToRemove = new HashSet<FireBall>();
        for (FireBall fireBall :fireBalls) {
           if (fireBall.checkCollision()) {
               setToRemove.add(fireBall);
               fireBall.remove();
           };
       }
       fireBalls.removeAll(setToRemove);

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

    public List<FireBall> getFireBalls() {
        return fireBalls;
    }

    public void setFireBalls(List<FireBall> fireBalls) {
        this.fireBalls = fireBalls;
    }

    public List<MyUnit> getUnits() {
        return units;
    }

    public void spawnWolfs(float x, float y) {
        Wolf wolf = new Wolf((int)x, (int)y);
        units.add(wolf);
        addActor(wolf);

    }
}
