package batyushka.model.units;

import batyushka.Engine;
import batyushka.model.MyStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Skeleton extends MyUnit {

    // private static short damage = 10;
    private static final TextureRegion texture;
    public static final int AGR_DISTANCE = 500;
    private static TextureRegion deadTexture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/skeleton.png")), 0, 0, 32, 32);
        deadTexture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/skeletonDead.png")), 0, 0, 32, 32);
    }

    private static final short MAX_HITPOINTS = 30;

    public Skeleton(int x, int y) {
        super(x, y);
        this.damage = 10;
        this.hitpoint = MAX_HITPOINTS;
        this.speed = 1;
    }


    @Override
    protected void update(float deltaTime) {
        if (isDead) {
            return;
        }
        Player player = ((MyStage) getStage()).getPlayer();
        if (!chaseAndAttack(player)) {
            super.update(deltaTime);
        }
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
    public Actor hit(float x, float y) {
        return null;
    }
    public static final int AGR_SPEED = 2;
    @Override
    protected short getAgrSpeed() {
        return AGR_SPEED;
    }

    @Override
    public float getAgrDistance() {
        return AGR_DISTANCE;
    }
}
