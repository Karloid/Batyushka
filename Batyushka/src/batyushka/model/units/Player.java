package batyushka.model.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.draw.ResurrectButton;
import batyushka.model.FireBall;
import batyushka.model.MyStage;

import java.util.HashMap;
import java.util.Map;

public class Player extends MyUnit {
    public static final int SPEED = 2;
    private static final TextureRegion texture;
    private boolean isMoving;
    private static TextureRegion deadTexture;
    static {
        deadTexture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/deadCharacter.png")), 0, 0, 32, 32);
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/character.png")), 0, 0, 32, 32);
    }


    public short killCount;

    /**
     * Здесь мы храним информацию про класс Stage, которому принадлежит данный актер.
     * Она будет необходима нам для выставления фокуса скроллинга и фокуса ввода с клавиатуры
     */

    public Player(int x, int y) {
        super(x, y);
        this.velocity = new short[2];
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.killCount = 0;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        updatePosition();
        if (isDead) {
            batch.draw(deadTexture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        } else {
            if (hitpoint < 30) {
                batch.setColor(1, 0.8f, 0.8f, 1);
            }
        batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);   }
        batch.setColor(1, 1, 1, 1);
        //    System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    @Override
    protected void updatePosition() {
        if (isDead) {
            return;
        }
        if (hitpoint <= 0) {
            isDead = true;
            velocity[0] = 0;
            velocity[1] = 0;
            stage.addActor(new ResurrectButton(this));
         //   stage.addActor(new PlayerCorpse(this));
        } else {
            updatePosWithCheckCollisions();
         //   super.updatePosition();
            updateCamera();
        }
    }

    @Override
    protected short getAgrSpeed() {
        return 0;
    }


    @Override
    public Actor hit(float x, float y) {
        /**
         * Стандартная процедура проверки. Если точка в прямоугольнике актера, возвращаем актера.
         */
        return x > 0 && x < width && y > 0 && y < height ? this : null;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        if (pointer == 0) {
            isMoving = true;
            move(x, y);
        }
        if (pointer == 1) {
            castFireBall(x, y);
        }
        if (pointer == 2) {
         //   ((MyStage) getStage()).spawnUnits(x + this.x, -y + this.y);
           ((MyStage) getStage()).createWall(x + this.x, -y + this.y);
        }
        return true;
    }

    private void castFireBall(float toX, float toY) {
        if (isDead) {
            return;
        }
        FireBall fireBall = new FireBall(x, y, toX, -(toY));
        ((MyStage) getStage()).getFireBalls().add(fireBall);
        getStage().addActor(fireBall);
    }

    @Override
    public void touchUp(float x, float y, int pointer) {
        if (pointer == 0) {
            isMoving = false;
            resetWay();
        }
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return true;
    }

    private void updateCamera() {
        Camera cam = getStage().getCamera();
        cam.position.set(x /*- (Engine.WINDOW_WIDTH / 4)*/, y /*- (Engine.WINDOW_HEIGHT / 4)*/, 0);
        cam.update();
    }

    @Override
    public void touchDragged(float x, float y, int pointer) {
        if (isMoving) {
            move(x, y);
        }
    }

    private void move(float toX, float toY) {
        resetWay();
        double angleToPoint = getAngleToPoint(toX, toY);
        //  System.out.println("debug: " + angleToPoint + " xy " + (toX - x) + " " +  (toY - y));

        if (angleToPoint < 55 || angleToPoint > 305) upPressed();

        if (angleToPoint > 35 && angleToPoint < 145) rightPressed();

        if (angleToPoint > 125 && angleToPoint < 235) downPressed();

        if (angleToPoint > 215 && angleToPoint < 325) leftPressed();

        processPosition();
    }

    private void processPosition() {
        if (direction.get(Keys.LEFT)) velocity[0] = -SPEED;


        if (direction.get(Keys.RIGHT)) velocity[0] = SPEED;

        if (direction.get(Keys.UP)) velocity[1] = SPEED;


        if (direction.get(Keys.DOWN)) velocity[1] = -SPEED;
        if ((direction.get(Keys.LEFT) && direction.get(Keys.RIGHT)) || (!direction.get(Keys.LEFT) && (!direction.get(Keys.RIGHT))))
            velocity[0] = 0;

        if ((direction.get(Keys.UP) && direction.get(Keys.DOWN)) || (!direction.get(Keys.UP) && (!direction.get(Keys.DOWN))))
            velocity[1] = 0;
    }

    public void resetWay() {
        rightReleased();
        leftReleased();
        downReleased();
        upReleased();

        velocity[0] = 0;
        velocity[1] = 0;
    }

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    static Map<Keys, Boolean> direction = new HashMap<Keys, Boolean>();

    static {
        direction.put(Keys.LEFT, false);
        direction.put(Keys.RIGHT, false);
        direction.put(Keys.UP, false);
        direction.put(Keys.DOWN, false);
    }

    ;


    public void leftPressed() {
        //    Log.i("BADGER", "LEFT PRESSED");
        direction.get(direction.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        //   Log.i("BADGER", "RIGHT PRESSED");
        direction.get(direction.put(Keys.RIGHT, true));
    }

    public void upPressed() {
        //    Log.i("BADGER", "UP PRESSED");
        direction.get(direction.put(Keys.UP, true));
    }

    public void downPressed() {
        //     Log.i("BADGER", "DOWN PRESSED");
        direction.get(direction.put(Keys.DOWN, true));
    }

    public void leftReleased() {
        direction.get(direction.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        direction.get(direction.put(Keys.RIGHT, false));
    }

    public void upReleased() {
        direction.get(direction.put(Keys.UP, false));
    }

    public void downReleased() {
        direction.get(direction.put(Keys.DOWN, false));
    }

    private double getAngleToPoint(float x, float y) {
        if (x == 0) {
            return (y > 0) ? 180 : 0;
        }
        double angleToPoint = Math.atan(y / x) * 180 / Math.PI;
        angleToPoint = (x > 0) ? angleToPoint + 90 : angleToPoint + 270;
        return angleToPoint;
    }

    protected void addKillCount() {

        killCount++;
    };
};