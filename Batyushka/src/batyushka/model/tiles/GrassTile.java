package batyushka.model.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import batyushka.model.MyStage;

public class GrassTile extends Tile {
    private static final TextureRegion texture;

    static {
        texture = new TextureRegion(new Texture(Gdx.files.internal("batyushka/res/grass.png")), 0, 0, 32, 32);
    }
    public GrassTile(int x, int y) {
      super(x,y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (MyStage.isCameraView(stage, x, y))
        batch.draw(texture, x - 32, y - 32, originX, originY, width, height, 1, 1, rotation);
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
