package batyushka;

import batyushka.model.FireBall;
import batyushka.model.MyStage;
import batyushka.model.staticobjects.*;
import batyushka.model.tiles.*;
import batyushka.model.units.*;
import batyushka.model.units.portals.WeakPortal;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class StageGenerator {
    private static int levelWidth;
    private static int levelHeight;
    private static ArrayList<Tile> tiles;
    private static ArrayList<MyUnit> staticObjects;
    private static MyStage stage;
    private static ArrayList<MyUnit> units;
    private static Player player;

    public static void generateStage(String levelName, MyStage stage) {
        StageGenerator.stage = stage;
        try {
            tiles = new ArrayList<Tile>();
            units = new ArrayList<MyUnit>();
            staticObjects = new ArrayList<MyUnit>();

            File urlImageInput = new File("batyushka/res/" + levelName + ".png");

            BufferedImage levelImage = ImageIO.read(urlImageInput);

            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -levelImage.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            levelImage = op.filter(levelImage, null);

            levelWidth = levelImage.getWidth();
            levelHeight = levelImage.getHeight();
            for (int x = 0; x < levelWidth; x++) {
                for (int y = 0; y < levelHeight; y++) {
                    int c = levelImage.getRGB(x, y);
                    int red = (c & 0x00ff0000) >> 16;
                    int green = (c & 0x0000ff00) >> 8;
                    int blue = c & 0x000000ff;
                    handleMapPixel(red, green, blue, x, y);
                }
            }
            injectIntoStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectIntoStage() {
        // stage.player = new Player(10 * MyStage.TILE_SIZE, 10 * MyStage.TILE_SIZE);
        stage.setFireBalls(new ArrayList<FireBall>());

        stage.setTiles(tiles);
        for (Tile tile : tiles) {
            stage.addActor(tile);
        }
        stage.setStaticObjects(staticObjects);
        for (MyUnit unit : staticObjects) {
            stage.addActor(unit);
        }
        stage.setUnits(units);
        for (MyUnit unit : units) {
            stage.addActor(unit);
        }
        units = new ArrayList<MyUnit>();

        stage.player = player;

        stage.addActor(player);
    }

    private static void handleMapPixel(int red, int green, int blue, int x, int y) {
        // UNITS
        if (red == 255) {
            player = new Player(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE);
            stage.resurrectPoint = new int[]{x, y};
        } else if (red == 200) {
            units.add(new Wolf(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (red == 150) {
            units.add(new Rabbit(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (red == 144) {
            units.add(new Skeleton(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        }else if (red == 160) {
            units.add(new WeakPortal(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        }   else if (red == 165) {
            units.add(new WeakDemon(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        }

        // TILES
        if (green == 255) {
            generateGrass(x, y);
        } else if (green == 100) {
            tiles.add(new WoodenFloor(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (green == 130) {
            tiles.add(new DirtyRoad(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (green == 110) {
            tiles.add(new WallGate(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (green == 120) {
            tiles.add(new WallArc(x * stage.TILE_SIZE, y * MyStage.TILE_SIZE));
        }

        // STATIC OBJECTS
        if (blue == 255) {
            staticObjects.add(new AppleTree(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 167) {
            staticObjects.add(new FirTree(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 100) {
            staticObjects.add(new Wall(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 200) {
            staticObjects.add(new Bed(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 174) {
            staticObjects.add(new Chest(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 120) {
            staticObjects.add(new WallWindow(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 180) {
            staticObjects.add(new Forge(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 130) {
            staticObjects.add(new WallCrest(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        } else if (blue == 135) {
            staticObjects.add(new WallSaintSource(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE));
        }
    }

    private static Tile generateGrass(int x, int y) {
        Tile tile = null;
        byte b = (byte) (Math.random() * 3);
        if (b == 0) {
            tile = new GrassTile(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE);
        } else if (b == 1) {
            tile = new GrassFlowersTile(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE);
        } else if (b == 2) {
            if (Math.random() > 0.9f) {
                tile = new GrassFlowersTile2(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE);
            } else {
                tile = new GrassTile(x * MyStage.TILE_SIZE, y * MyStage.TILE_SIZE);
            }
        }
        tiles.add(tile);
        return null;
    }
}
