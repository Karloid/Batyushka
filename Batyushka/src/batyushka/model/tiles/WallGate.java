package batyushka.model.tiles;

import batyushka.model.MyStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WallGate extends Tile {
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/wallGate.png")), 0, 0, 32, 32);
    }

    public WallGate(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
            batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
    }
}
