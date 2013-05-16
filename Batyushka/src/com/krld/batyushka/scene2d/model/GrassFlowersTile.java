package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GrassFlowersTile extends Tile {
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/grassFlowers.png")), 0, 0, 32, 32);
    }
    public GrassFlowersTile(int x, int y) {
      super(x,y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(texture, x, y, originX, originY, width, height, 1, 1, rotation);
        batch.setColor(1, 1, 1, 1);

    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
