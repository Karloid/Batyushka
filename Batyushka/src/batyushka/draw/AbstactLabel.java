package batyushka.draw;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.Engine;
import batyushka.model.MyStage;
import batyushka.model.units.MyUnit;

public abstract class AbstactLabel extends MyUnit {
    private final String amount;
    private long birthDate;
    public static final int LIFE_TIME = 600;

    public AbstactLabel(float x, float y, int amount) {
        velocity = new short[2];
        velocity[0] = 2;
        velocity[1] = 2;
        this.x = x;
        this.y = y;
        String tmp = String.valueOf(amount);
        if (amount > 0) {
            this.amount = "+" + tmp;
        } else {
            this.amount = tmp;
        }
        birthDate = System.currentTimeMillis();
    }

    @Override
    protected void updatePosition() {
        //   super.updatePosition();
        x += velocity[0];
        y += velocity[1];
        if (System.currentTimeMillis() - birthDate > LIFE_TIME) {
            ((MyStage) getStage()).getFireBalls().remove(this);
            this.remove();
        }
    }

    @Override
    protected short getAgrSpeed() {
        return 0;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        updatePosition();
        //   batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        // batch.setColor(1, 0.5f, 0.5f, 1);
        Engine.font.setColor(getRed(), getGreen(), getBlue(), 1);
        Engine.font.draw(batch, amount, x - 32, y + 64);
        Engine.font.setColor(1, 1, 1, 1);
        // batch.setColor(1, 1, 1, 1);
    }

    protected abstract float getBlue();

    protected abstract float getGreen();

    protected abstract float getRed();


    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
