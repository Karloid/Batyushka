package batyushka.model.staticobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import batyushka.model.MyStage;

public class FirTree extends StaticObject {
    private static TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/fir.png")), 0, 0, 32, 32);
    }

    public FirTree(int x, int y) {
        super(x, y);
    }

    @Override
    protected void dropSomething() {

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        // Engine.font.draw(batch, "test", x - 32, y + 64);
    }
}
