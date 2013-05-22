package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.krld.batyushka.scene2d.Engine;
import com.krld.batyushka.scene2d.model.staticobjects.AppleTree;
import com.krld.batyushka.scene2d.model.tiles.GrassFlowersTile;
import com.krld.batyushka.scene2d.model.tiles.GrassTile;
import com.krld.batyushka.scene2d.model.tiles.Tile;
import com.krld.batyushka.scene2d.model.units.MyUnit;
import com.krld.batyushka.scene2d.model.units.Wolf;

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
    private List<MyUnit> staticObjects;
    private List<Tile> tiles;

    public MyStage(int windowWidth, int windowHeight, boolean b, SpriteBatch batch) {
        super(windowWidth, windowHeight, b, batch);
        Texture textureAtlas = new Texture(Gdx.files.internal("batyushka/res/character.png"));
        TextureRegion characterTexture = new TextureRegion(textureAtlas, 0, 0, 32, 32);

        generateWorld();
        setFireBalls(new ArrayList<FireBall>());
        units = new ArrayList<MyUnit>();
        player = new Player(50 * TILE_SIZE, 50 * TILE_SIZE, characterTexture);
        addActor(getPlayer());
        Wolf wolf = new Wolf(53 * TILE_SIZE, 53 * TILE_SIZE);
        units.add(wolf);
        addActor(wolf);
        //   wolf.action(new FadeOut().);
        setKeyboardFocus(getPlayer());
        //    world.
    }

    private void generateWorld() {
        tiles = new ArrayList<Tile>();
        staticObjects = new ArrayList<MyUnit>();
        for (int x = 0; x < MyStage.SIZE; x++) {
            for (int y = 0; y < MyStage.SIZE; y++) {
                tileMap[x][y] = (byte) (Math.random() * 2);
                addTile(tileMap[x][y], x, y);
                if (Math.random() > 0.9f) {
                    staticObjects.add(new AppleTree(x * TILE_SIZE, y * TILE_SIZE));
                }
            }
        }
        for (MyUnit myUnit : staticObjects) {
            //System.out.println("add tree");
            addActor(myUnit);
        }
    }

    private void addTile(byte b, int x, int y) {
        if (b == 0) {
            GrassTile grassTile = new GrassTile(x * TILE_SIZE, y * TILE_SIZE);
            addActor(grassTile);
            tiles.add(grassTile);
        } else if (b == 1) {
            GrassFlowersTile grassTile = new GrassFlowersTile(x * TILE_SIZE, y * TILE_SIZE);
            addActor(grassTile);
            tiles.add(grassTile);
        }
    }

    @Override
    public void draw() {
        checkFireballCollisions();
        //sortingEntity();

        super.draw();
    }

    private void sortingEntity() {
        List<Actor> actors = getActors();
        //actors.
      //  actors.addAll(tiles);
        //actors.add(player);
       // actors.addAll(staticObjects);
       // actors.addAll(units);



    }

    private void checkFireballCollisions() {
        Set<FireBall> setToRemove = new HashSet<FireBall>();
        for (FireBall fireBall : fireBalls) {
            if (fireBall.checkCollision()) {
                setToRemove.add(fireBall);
                fireBall.remove();
            }
            ;
        }
        fireBalls.removeAll(setToRemove);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        getPlayer().touchDown(x - (Engine.WINDOW_WIDTH / 2), (y - (Engine.WINDOW_HEIGHT / 2)), button);
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        getPlayer().touchDragged(x - (Engine.WINDOW_WIDTH / 2), (y - (Engine.WINDOW_HEIGHT / 2)), pointer);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        getPlayer().touchUp(x, y, button);
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
        Wolf wolf = new Wolf((int) x, (int) y);
        units.add(wolf);
        addActor(wolf);
    }

    public Player getPlayer() {
        return player;
    }
}
