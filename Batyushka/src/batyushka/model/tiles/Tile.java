package batyushka.model.tiles;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Tile extends Actor {

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
    }



    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
