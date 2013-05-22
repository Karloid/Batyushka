package com.krld.batyushka.scene2d.model.units;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.model.*;

import java.util.List;

public abstract class MyUnit extends Actor {
    private static final float RANDOM_PART_DAMAGE = 0.3f;
    private static final long MIN_TIME_MOVING = 1000;
    public static final int AGR_DISTANCE = 200;

    protected short[] velocity;
    protected short hitpoint;
    private short SPEED = 2;
    protected long startMoving;


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


    protected void update(float deltaTime) {
        // System.out.println(deltaTime);
        if (velocity[0] == 0 && velocity[1] == 0) {
            if (Math.random() > 0.99f) {
                short xVelocity = (short) ((Math.round(Math.random() * 2f) - 1) * SPEED);
                short yVelocity = (short) ((Math.round(Math.random() * 2f) - 1) * SPEED);
                velocity[0] = xVelocity;
                velocity[1] = yVelocity;
                startMoving = System.currentTimeMillis();
            }
        } else if (System.currentTimeMillis() - startMoving > MIN_TIME_MOVING && Math.random() > 0.95f) {
            velocity[0] = 0;
            velocity[1] = 0;
        }

        updatePosition();
    }

    protected void updatePosition() {
        x += velocity[0];
        y += velocity[1];
    }

    protected boolean attackPlayer() {
        Player player = ((MyStage) getStage()).getPlayer();
        float yRelative = y - player.y;
        float xRelative = x - player.x;
        float absX = Math.abs(xRelative);
        float absY = Math.abs(yRelative);
        if (absX < AGR_DISTANCE && absY < AGR_DISTANCE) {
            System.out.println("agr");
            if (absX < 32 && absY < 32) {
                stopMove();
            } else {
            moveTo(-xRelative, -yRelative);      }
            System.out.println("velociy: " + velocity[0] + " " + velocity[1]);
            updatePosition();
            return true;
        }
        return false;
    }

    protected void stopMove() {
        velocity[0] = 0;
        velocity[1] = 0;
    }

    private void moveTo(float toX, float toY) {
        float tmpX = Math.abs(toX);
        float tmpY = Math.abs(toY);
        float divider;

        if (tmpX > tmpY) {
            divider = SPEED / tmpX;
        } else {
            divider = SPEED / tmpY;
        }
        velocity[0] = (short) (toX * divider);
        velocity[1] = (short) (toY * divider);
    }

    public void fireBallHit(FireBall fireBall) {
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