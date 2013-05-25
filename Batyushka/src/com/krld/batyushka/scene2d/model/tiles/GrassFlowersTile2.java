package com.krld.batyushka.scene2d.model.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.krld.batyushka.scene2d.model.MyStage;

public class GrassFlowersTile2 extends GrassTile {
    public static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/grassFlowers2.png")), 0, 0, 32, 32);
    }

  public GrassFlowersTile2(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
    }
   /*
    @Override
    public Actor hit(float x, float y) {
        return null;
    }*/
}
