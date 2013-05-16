package com.krld.batyushka.scene2d.model.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
