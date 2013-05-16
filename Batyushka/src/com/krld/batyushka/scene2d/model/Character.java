package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Map;

class Character extends Actor {
    public static final int SPEED = 2;
    private final TextureRegion texture;
    private short[] velocity;

    /**
     * Здесь мы храним информацию про класс Stage, которому принадлежит данный актер.
     * Она будет необходима нам для выставления фокуса скроллинга и фокуса ввода с клавиатуры
     */

    public Character(int x, int y, TextureRegion texture) {
        this.velocity = new short[2];
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlfa) {
        updatePosition();
        batch.draw(texture, x, y, originX, originY, width, height, 1, 1, rotation);
        batch.setColor(1, 1, 1, 1);
    //    System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    private void updatePosition() {
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
/*        *//**
         * Если мы нажали на актера, мы увеличиваем его и передаем ему фокус ввода с клавиатуры и фокус скроллинга
         *//*
        System.out.println(pointer);
        scaleX += 2;
        scaleY += 2;
        getStage().setTouchFocus(this, 1);
        getStage().setScrollFocus(this);
        getStage().setKeyboardFocus(this);

        *//**
         * Вращать будем вокруг точки, на которую мы нажали
         *//*
        originX = x;
        originY = y;

        return true;*/
        move(x, y);
        return true;
    }

    @Override
    public void touchUp(float x, float y, int pointer) {
        resetWay();
    }

    @Override
    public boolean scrolled(int amount) {
        /**
         * При вращении колеса мыши вращаем актера
         */
        rotation += (amount * 20);
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        /**
         * Когда мы нажимаем на кнопки, стандартные для шутеров, наш актер двигается
         */
        switch (c) {
            case 'a':
                x -= 10;
                break;
            case 'd':
                x += 10;
                break;
            case 's':
                y -= 10;
                break;
            case 'w':
                y += 10;
                break;
        }

        updateCamera();
        return true;
    }

    private void updateCamera() {
        Camera cam = getStage().getCamera();
        cam.position.set(x /*- (Engine.WINDOW_WIDTH / 4)*/, y /*- (Engine.WINDOW_HEIGHT / 4)*/, 0);
        cam.update();
    }

    @Override
    public void touchDragged(float x, float y, int pointer) {
        move(x, y);
    }

    private void move(float toX, float toY) {
        resetWay();
        /*
        if (Math.abs(x) < 100 && Math.abs(y) < 100) {
            return;
        } */
        double angleToPoint = getAngleToPoint(toX, toY);
        //  System.out.println("debug: " + angleToPoint + " xy " + (toX - x) + " " +  (toY - y));
        //   Log.i("BADGER", "Angle to point" + angleToPoint);
 /*       if (y < getY()) upPressed();

        if (y > getY()) downPressed();

        if (x < getX()) leftPressed();

        if (x > (getPosition().x + size) * world.ppuX) rightPressed()*/
        ;
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