package batyushka.draw;

public class MPRestoreLabel extends AbstactLabel {

    public MPRestoreLabel(float x, float y, int healValue) {
        super(x, y, healValue);
    }

    @Override
    protected float getBlue() {
        return 1;
    }

    @Override
    protected float getGreen() {
        return 0.5f;
    }

    @Override
    protected float getRed() {
        return 0.5f;
    }
}
