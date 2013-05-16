package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FireBall extends Actor {
    private short[] velocity;

    public FireBall(float x, float y, float toX, float toY) {
        this.x = x;
        this.y = y;
        this.width = 16;
        this.height = 16;
        velocity = new short [2];
        System.out.println(toX + " " + toY);
        velocity[0] = (short) (toX /10);
        velocity[1] = (short) (toY / 10);


    }
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/fireBall.png")), 0, 0, 8, 8);
    }
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        updatePosition();
        batch.draw(texture, x, y, originX, originY, width, height, 1, 1, rotation);
        batch.setColor(1, 1, 1, 1);

    }

    private void updatePosition() {
        x += velocity[0];
        y += velocity[1];

    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
