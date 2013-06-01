package batyushka.draw;

public class HealLabel extends AbstactLabel {

    public HealLabel(float x, float y, int healValue) {
        super(x, y, healValue);
    }

    @Override
    protected float getBlue() {
        return 0.5f;
    }

    @Override
    protected float getGreen() {
        return 1;
    }

    @Override
    protected float getRed() {
        return 0.5f;
    }
}
