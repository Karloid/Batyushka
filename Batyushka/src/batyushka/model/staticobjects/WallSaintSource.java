package batyushka.model.staticobjects;

import batyushka.model.FireBall;
import batyushka.model.MyStage;
import batyushka.model.units.MyUnit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WallSaintSource extends StaticObject implements HealingObject {
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/wallSaintSource.png")), 0, 0, 32, 32);
    }

    private static final short RESTORE_MP_VALUE = 10;

    public WallSaintSource(int x, int y) {
        super(x, y);
        this.hitpoint = 1000;
    }

    @Override
    protected void dropSomething() {

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
    }

    @Override
    public void fireBallHit(FireBall fireBall) {
        MyUnit player = ((MyStage)stage).getPlayer();
        player.restoreMP(RESTORE_MP_VALUE);
    }
}
