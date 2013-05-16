package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

class Character extends Actor {
    private final TextureRegion texture;

    /**
     * Здесь мы храним информацию про класс Stage, которому принадлежит данный актер.
     * Она будет необходима нам для выставления фокуса скроллинга и фокуса ввода с клавиатуры
     */

    public Character(int x, int y, TextureRegion texture) {
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlfa) {
        batch.draw(texture, x, y, originX, originY, width, height, 1, 1, rotation);
        batch.setColor(1, 1, 1, 1);
       // System.out.println(Gdx.graphics.getFramesPerSecond());
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
        /**
         * Если мы нажали на актера, мы увеличиваем его и передаем ему фокус ввода с клавиатуры и фокус скроллинга
         */
        scaleX += 2;
        scaleY += 2;
        getStage().setTouchFocus(this, 1);
        getStage().setScrollFocus(this);
        getStage().setKeyboardFocus(this);

        /**
         * Вращать будем вокруг точки, на которую мы нажали
         */
        originX = x;
        originY = y;

        return true;
    }

    @Override
    public void touchUp(float x, float y, int pointer) {
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
        return true;
    }
};