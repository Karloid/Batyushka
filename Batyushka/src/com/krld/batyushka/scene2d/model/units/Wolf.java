package com.krld.batyushka.scene2d.model.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.Engine;
import com.krld.batyushka.scene2d.model.FireBall;

public class Wolf extends MyUnit {


    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/wolf.png")), 0, 0, 32, 32);
    }

    public Wolf(int x, int y) {
        super(x, y);
    }

    @Override
    public void fireBallHit(FireBall fireBall) {
        super.fireBallHit(fireBall);
    }

    @Override
    protected void update(float deltaTime) {
        if (!attackPlayer()) {
        super.update(deltaTime);  }

    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        update(Gdx.graphics.getDeltaTime());
        batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        Engine.font.draw(batch, hitpoint + "", x - 32,y + 64);
        batch.setColor(1, 1, 1, 1);
    }


    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
