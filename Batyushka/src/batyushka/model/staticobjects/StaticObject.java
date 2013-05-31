package batyushka.model.staticobjects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.Engine;
import batyushka.model.MyStage;
import batyushka.model.units.MyUnit;

public abstract class StaticObject extends MyUnit {

    public static final float DAMAGE_DECREASE = 0.7f;

    public StaticObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Engine.TILE_SIZE;
        this.height = Engine.TILE_SIZE;
        hitpoint = 200;
        damageMultipliyer = DAMAGE_DECREASE;


    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }

    public void destroy() {
        dropSomething();
        ((MyStage)stage).getStaticObjects().remove(this);
        remove();
    }

    protected abstract void dropSomething();
}
