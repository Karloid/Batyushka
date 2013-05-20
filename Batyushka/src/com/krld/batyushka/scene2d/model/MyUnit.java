package com.krld.batyushka.scene2d.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public abstract class MyUnit extends Actor {
    private static final float RANDOM_PART_DAMAGE = 0.3f;

    protected short[] velocity;
    protected short hitpoint;


    public MyUnit(int x, int y) {
        this.velocity = new short[2];
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        hitpoint = 100;

    }

    protected MyUnit() {
    }

    protected void updatePosition() {
        x += velocity[0];
        y += velocity[1];

    }

    public  void fireBallHit(FireBall fireBall) {
        int damage = (int) Math.floor(fireBall.DAMAGE - (fireBall.DAMAGE * MyUnit.RANDOM_PART_DAMAGE * Math.random()));
        hitpoint -= damage;
        getStage().addActor(new DamageLabel(this.x, this.y, damage));
        if (hitpoint <= 0) {
            List<MyUnit> units = ((MyStage) getStage()).getUnits();
            units.remove(this);
            this.remove();
        }
    }

    @Override
    public void remove() {
        super.remove();

    }
}
