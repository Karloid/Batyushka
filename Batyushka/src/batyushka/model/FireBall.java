package batyushka.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.model.units.MyUnit;

public class FireBall extends Actor {
    private static final float SPEED = 5;
    public static final short DAMAGE = 20;
    public static final int LIFE_TIME = 400;
    private final long birthDate;
    private short[] velocity;

    public FireBall(float x, float y, float toX, float toY) {
        this.x = x;
        this.y = y;
        this.width = 16;
        this.height = 32;
        velocity = new short[2];
        setupVelocity(toX, toY);
        birthDate = System.currentTimeMillis();
    }

    private void setupVelocity(float toX, float toY) {
        float tmpX = Math.abs(toX);
        float tmpY = Math.abs(toY);
        float divider;

        if (tmpX > tmpY) {
            divider = SPEED / tmpX;
        } else {
            divider = SPEED / tmpY;
        }
        velocity[0] = (short) (toX * divider);
        velocity[1] = (short) (toY * divider);
    }

    private static final TextureRegion texture;

    static {
      //  texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/fireBall.png")), 0, 0, 8, 8);
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/crest.png")), 0, 0, 8, 16);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        updatePosition();
        batch.draw(texture, x - 8, y - 8, originX, originY, width, height, 1, 1, rotation);
    }

    private void updatePosition() {
        x += velocity[0];
        y += velocity[1];
        if (System.currentTimeMillis() - birthDate > LIFE_TIME) {
            ((MyStage) getStage()).getFireBalls().remove(this);
            this.remove();
        }
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }

    public boolean checkCollision() {
        MyStage myStage = (MyStage) getStage();
        for (MyUnit myUnit : myStage.getUnits()) {
            if (32 > Math.abs(myUnit.x - this.x) && 32 > Math.abs(myUnit.y - this.y)) {
                myUnit.fireBallHit(this);
                return true;
            }
        }
        for (MyUnit staticObject : myStage.getStaticObjects()) {
            if (32 > Math.abs(staticObject.x - this.x) && 32 > Math.abs(staticObject.y - this.y)) {
                staticObject.fireBallHit(this);
                return true;
            }
        }

        return false;
    }
}
