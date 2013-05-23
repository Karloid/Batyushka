package com.krld.batyushka.scene2d.draw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.model.MyStage;
import com.krld.batyushka.scene2d.model.units.Player;

public class ResurrectButton extends Actor {
    private static TextureRegion texture;
    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/resurrectButton.png")), 0, 0, 128, 32);
    }

    public ResurrectButton(Player player) {
        this.x = player.x - 128;
        this.y = player.y - 256;
        this.width = 256;
        this.height = 64;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(texture, x, y, originX, originY, width, height, 1, 1, rotation);
    }

    @Override
    public Actor hit(float x, float y) {
        //System.out.println("ttt");
        ((MyStage)stage).resurrectPlayer();
        remove();
        return null;
    }
}
