package batyushka.model.staticobjects;

import batyushka.model.MyStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bed extends StaticObject {
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/bed.png")), 0, 0, 32, 32);
    }
    public Bed(int x, int y) {
        super(x,y);
    }

    @Override
    protected void dropSomething() {

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
    }
}
