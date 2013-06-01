package batyushka.model.units;

import batyushka.model.FireBall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.Engine;
import batyushka.model.MyStage;

public class Rabbit extends MyUnit implements Allied {
    private static final TextureRegion texture;

    private static TextureRegion deadTexture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/rabbit.png")), 0, 0, 32, 32);
        deadTexture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/rabbitDead.png")), 0, 0, 32, 32);
    }

    private static final short MAX_HITPOINTS = 30;

    public Rabbit(int x, int y) {
        super(x, y);
        this.damage = 0;
        this.hitpoint = MAX_HITPOINTS;
        this.speed = peacefullSpeed;
    }


    @Override
    protected void update(float deltaTime) {
        if (isDead) {
            return;
        }
        super.update(deltaTime);
    }

    public static final int AGR_SPEED = 1;
    @Override
    protected short getAgrSpeed() {
        return AGR_SPEED;
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y)) {
            update(Gdx.graphics.getDeltaTime());
            if (isDead) {
                batch.draw(deadTexture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
            } else {
                batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
            }
            if (hitpoint != MAX_HITPOINTS && !isDead) {
                Engine.font.draw(batch, hitpoint + "", x - 32, y + 64);
            }
        }
    }

    @Override
    public void fireBallHit(FireBall fireBall) {
        this.heal((short) (fireBall.DAMAGE * 0.4f));
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
