package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.Engine;

import java.util.HashMap;
import java.util.Map;

class Player extends MyUnit {
    public static final int SPEED = 2;
    private final TextureRegion texture;
    private boolean isMoving;

    /**
     * Здесь мы храним информацию про класс Stage, которому принадлежит данный актер.
     * Она будет необходима нам для выставления фокуса скроллинга и фокуса ввода с клавиатуры
     */

    public Player(int x, int y, TextureRegion texture) {
        super(x, y);
        this.velocity = new short[2];
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        updatePosition();
        batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        Engine.font.draw(batch, hitpoint + "", x - 32,y + 64);
        batch.setColor(1, 1, 1, 1);
        //    System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    @Override
    protected void updatePosition() {
        x += velocity[0];
        y += velocity[1];
        updateCamera();
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
            ((MyStage)getStage()).spawnWolfs(x + this.x,-y + this.y);
        }
        return true;
    }

    private void castFireBall(float toX, float toY) {
        FireBall fireBall = new FireBall(x, y , toX , -(toY ));
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
};