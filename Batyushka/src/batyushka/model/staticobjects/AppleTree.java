package batyushka.model.staticobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import batyushka.model.MyStage;

public class AppleTree extends StaticObject {
    private static final TextureRegion textureWithApples;

    private static final TextureRegion textureNoApples;

    static {
        textureWithApples = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/appleTreeSingle.png")), 0, 0, 32, 32);
        textureNoApples = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/appleTree.png")), 0, 0, 32, 32);
    }

    private boolean haveApples;

    public AppleTree(int x, int y) {
        super(x, y);
        if (Math.random() < 0.3f) {
            haveApples = true;
        } else {
            haveApples = false;
        }
    }

    @Override
    protected void dropSomething() {
        ((MyStage) stage).spawnUnits(x, y);
        ((MyStage) stage).spawnUnits(x, y);
        ((MyStage) stage).spawnUnits(x, y);
        ((MyStage) stage).spawnUnits(x, y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            if (haveApples) batch.draw(textureWithApples, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
            else batch.draw(textureNoApples, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
        // Engine.font.draw(batch, "test", x - 32, y + 64);
    }
}
