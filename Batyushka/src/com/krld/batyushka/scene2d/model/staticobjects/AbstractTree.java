package com.krld.batyushka.scene2d.model.staticobjects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.model.units.MyUnit;

public abstract class AbstractTree extends MyUnit {

    public static final int ABSTRACT_TREE_SIZE = 64;

    public AbstractTree(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ABSTRACT_TREE_SIZE;
        this.height = ABSTRACT_TREE_SIZE;
        hitpoint = 1000;


    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
