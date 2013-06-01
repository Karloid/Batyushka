package batyushka.model.units.portals;

import batyushka.Engine;
import batyushka.model.MyStage;
import batyushka.model.units.MyUnit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AbstractHellPortal extends MyUnit {
    private static final TextureRegion texture;

    private static TextureRegion deadTexture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/hellPortal.png")), 0, 0, 32, 32);
        deadTexture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/hellPortalDead.png")), 0, 0, 32, 32);
    }

    private static final short MAX_HITPOINTS = 350;
    private long lastSpawnTime;

    public AbstractHellPortal(int x, int y) {
        super(x, y);
        this.damage = 0;
        this.hitpoint = MAX_HITPOINTS;
        this.speed = 0;
        this.peacefullSpeed = 0;
    }


    @Override
    protected void update(float deltaTime) {

        if (isDead) {
            return;
        }
        if (!MyStage.isCameraView(stage, x, y)) {
            return;
        }
        if (hitpoint <= 0) {
            isDead = true;
            ((MyStage)stage).getUnits().remove(this);
            return;
        }
        spawnSomething();
      //  super.update(deltaTime);
    }

    private void spawnSomething() {
        if (System.currentTimeMillis() - lastSpawnTime > getSpawnDelay()) {
            spawnCreeps();
            lastSpawnTime = System.currentTimeMillis();
        }
    }

    protected abstract long getSpawnDelay();

    protected abstract void spawnCreeps();

    public static final int AGR_SPEED = 0;

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
    public Actor hit(float x, float y) {
        return null;
    }
}
