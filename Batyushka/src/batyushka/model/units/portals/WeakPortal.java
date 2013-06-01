package batyushka.model.units.portals;

import batyushka.model.MyStage;
import batyushka.model.units.MyUnit;
import batyushka.model.units.Skeleton;
import batyushka.model.units.WeakDemon;

import java.util.List;

public class WeakPortal extends AbstractHellPortal {

    private static final long SPAWN_DELAY = 3000;
    private static short SPAWN_COUNT = 1;

    public WeakPortal(int x, int y) {
        super(x, y);
    }

    @Override
    protected long getSpawnDelay() {
        return SPAWN_DELAY;
    }

    @Override
    protected void spawnCreeps() {
        MyStage myStage = (MyStage) stage;
        List<MyUnit> units = myStage.getUnits();
        for (int i = 0; i < SPAWN_COUNT; i++) {
            MyUnit unit;
            if (Math.random() > 0.3f) {
                unit = new Skeleton((int) x, (int) y);
            } else {
                unit = new WeakDemon((int) x, (int) y);
            }
            units.add(unit);
            myStage.addActor(unit);
        }
        myStage.getPlayer().remove();
        myStage.addActor(myStage.getPlayer());
    }
}
