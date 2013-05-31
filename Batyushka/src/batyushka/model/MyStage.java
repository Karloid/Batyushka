package batyushka.model;

import batyushka.StageGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import batyushka.Engine;
import batyushka.model.staticobjects.AppleTree;
import batyushka.model.staticobjects.Wall;
import batyushka.model.tiles.GrassFlowersTile;
import batyushka.model.tiles.GrassFlowersTile2;
import batyushka.model.tiles.GrassTile;
import batyushka.model.tiles.Tile;
import batyushka.model.units.MyUnit;
import batyushka.model.units.Player;
import batyushka.model.units.Rabbit;
import batyushka.model.units.Wolf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyStage extends Stage {
    private ShapeRenderer renderer;
    private static final int SIZE = 100;
    public static final short TILE_SIZE = 64;
    private final byte[][] tileMap = new byte[SIZE][SIZE];
    public Player player;
    private List<FireBall> fireBalls;
    private List<MyUnit> units;
    private List<MyUnit> staticObjects;
    private List<Tile> tiles;
    public int[] resurrectPoint;


    public MyStage(int windowWidth, int windowHeight, boolean b, SpriteBatch batch) {
        super(windowWidth, windowHeight, b, batch);
        renderer = new ShapeRenderer();
       // generateWorld();
        StageGenerator.generateStage("level_01", this);
        setKeyboardFocus(getPlayer());
    }

    private void generateWorld() {
        tiles = new ArrayList<Tile>();
        setStaticObjects(new ArrayList<MyUnit>());
        for (int x = 0; x < MyStage.SIZE; x++) {
            for (int y = 0; y < MyStage.SIZE; y++) {
                tileMap[x][y] = (byte) (Math.random() * 3);
                addTile(tileMap[x][y], x, y);
                if (Math.random() > 0.9f) {
                    getStaticObjects().add(new AppleTree(x * TILE_SIZE, y * TILE_SIZE));
                }
            }
        }
        for (MyUnit myUnit : getStaticObjects()) {
            //System.out.println("add tree");
            addActor(myUnit);
        }
        setFireBalls(new ArrayList<FireBall>());
        units = new ArrayList<MyUnit>();
        player = new Player(50 * TILE_SIZE, 50 * TILE_SIZE);
        Wolf wolf = new Wolf(53 * TILE_SIZE, 53 * TILE_SIZE);
        units.add(wolf);
        addActor(wolf);
        addActor(getPlayer());
    }

    private void addTile(byte b, int x, int y) {
        Tile tile = null;
        if (b == 0) {
            tile = new GrassTile(x * TILE_SIZE, y * TILE_SIZE);
        } else if (b == 1) {
            tile = new GrassFlowersTile(x * TILE_SIZE, y * TILE_SIZE);
        } else if (b == 2) {
            if (Math.random() > 0.9f) {
                tile = new GrassFlowersTile2(x * TILE_SIZE, y * TILE_SIZE);
            } else {
                tile = new GrassTile(x * TILE_SIZE, y * TILE_SIZE);
            }
        }
        addActor(tile);
        tiles.add(tile);
    }

    @Override
    public void draw() {
        checkFireballCollisions();
        //sortingEntity();
        super.draw();
        batch.begin();
        Engine.font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), player.x - 400, player.y + 300);
        Engine.font.draw(batch, "HP: " + player.hitpoint, player.x - 400, player.y - 270);
        Engine.font.draw(batch, String.valueOf(player.killCount), camera.position.x + 350, camera.position.y + 300);
        batch.end();
    }

    private void drawResurectButton() {

      /*  System.out.println("draw button");
//        batch.end();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(0, 0, 0);

        renderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        renderer.setColor(Color.LIGHT_GRAY);
        renderer.filledRect( player.x + DrawHelper.BUTTON_X, player.y + DrawHelper.BUTTON_Y, DrawHelper.BUTTON_WIDTH,
                DrawHelper.BUTTON_HEIGHT);
       // Engine.font.draw(batch, )
        renderer.end();
        batch.begin();
        Engine.font.draw(batch, "Ressurect", player.x + DrawHelper.BUTTON_X, player.y + DrawHelper.BUTTON_Y + 32);
        batch.end();
      //  batch.begin();*/
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
        if (player.isDead) {
            return super.touchDown(x, y, pointer, button);
        } else {
            getPlayer().touchDown(x - (Engine.WINDOW_WIDTH / 2), (y - (Engine.WINDOW_HEIGHT / 2)), button);
            return true;
        }
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

    public void spawnUnits(float x, float y) {
        MyUnit myUnit = null;
        if (Math.random() > 0.5f) {
            myUnit = new Wolf((int) x, (int) y);
        } else {
            myUnit = new Rabbit((int) x, (int) y);
        }
        units.add(myUnit);
        addActor(myUnit);
        //TODO sorting actors on Y axis
        player.remove();
        addActor(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void resurrectPlayer() {
        //   player.remove();
        player = new Player(resurrectPoint[0] * TILE_SIZE, resurrectPoint[1] * TILE_SIZE);
        addActor(getPlayer());
    }

    public static boolean isCameraView(Stage stage, float x, float y) {
        return stage.getCamera().position.x - 650 < x && stage.getCamera().position.y - 450 < y &&
                stage.getCamera().position.x + 650 > x && stage.getCamera().position.y + 450 > y;
    }

    public List<MyUnit> getStaticObjects() {
        return staticObjects;
    }

    public void setStaticObjects(List<MyUnit> staticObjects) {
        this.staticObjects = staticObjects;
    }

    public void createWall(float x, float y) {
        MyUnit unit = null;
        unit = new Wall((int)x,(int)y);
        staticObjects.add(unit);
        addActor(unit);
        //TODO sorting actors on Y axis
        player.remove();
        addActor(player);

    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setUnits(ArrayList<MyUnit> units) {
        this.units = units;
    }
}
