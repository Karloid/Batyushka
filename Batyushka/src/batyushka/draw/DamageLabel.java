package batyushka.draw;

public class DamageLabel extends AbstactLabel {
    public DamageLabel(float x, float y, int damage) {
        super(x, y, -damage);
    }

    @Override
    protected float getBlue() {
        return 0.5f;
    }

    @Override
    protected float getGreen() {
        return 0.5f;
    }

    @Override
    protected float getRed() {
        return 1;
    }
}
