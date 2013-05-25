package com.krld.batyushka.scene2d.model.units;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.krld.batyushka.scene2d.Engine;
import com.krld.batyushka.scene2d.draw.DamageLabel;
import com.krld.batyushka.scene2d.model.FireBall;
import com.krld.batyushka.scene2d.model.MyStage;
import com.krld.batyushka.scene2d.model.staticobjects.StaticObject;

public abstract class MyUnit extends Actor {
    private static final float RANDOM_PART_DAMAGE = 0.3f;
    private static final long MIN_TIME_MOVING = 1000;
    public static final int AGR_DISTANCE = 200;
    private static final short ATTACK_DELAY = 1000;
    public static final int AGR_SPEED = 3;
    public static final int PEACEFULL_SPEED = 2;
    public static final int COLLISIONS_WIDTH = Engine.TILE_SIZE - 16;
    public boolean isDead = false;
    protected short[] velocity;
    public short hitpoint;
    protected short speed = 2;
    protected long startMoving;
    protected short damage;
    private long lastAttackTime = 0;
    protected boolean isAgred = false;
    protected double damageMultipliyer;


    public MyUnit(int x, int y) {
        this.velocity = new short[2];
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.damageMultipliyer = 1f;
        hitpoint = 100;
        damage = 10;
    }

    protected MyUnit() {
    }


    protected void update(float deltaTime) {
        // System.out.println(deltaTime);
        if (isDead) {
            return;
        }
        if (hitpoint <= 0) {
            isDead = true;
            ((MyStage) stage).getUnits().remove(this);
            return;
        }
        if (velocity[0] == 0 && velocity[1] == 0) {
            if (Math.random() > 0.99f) {
                short xVelocity = (short) ((Math.round(Math.random() * 2f) - 1) * speed);
                short yVelocity = (short) ((Math.round(Math.random() * 2f) - 1) * speed);
                velocity[0] = xVelocity;
                velocity[1] = yVelocity;
                startMoving = System.currentTimeMillis();
            }
        } else if (System.currentTimeMillis() - startMoving > MIN_TIME_MOVING && Math.random() > 0.95f) {
            velocity[0] = 0;
            velocity[1] = 0;
        }

        updatePosWithCheckCollisions();
    }

    protected void updatePosition() {
        x += velocity[0];
        y += velocity[1];
    }

    protected boolean chaseAndAttack(MyUnit unit) {
        if (isDead) {
            return false;
        }
        if (hitpoint <= 0) {
            isDead = true;
            ((MyStage) stage).getUnits().remove(this);
            return false;
        }
        if (unit.hitpoint <= 0) {
            isAgred = false;
            speed = PEACEFULL_SPEED;
            return false;
        }
        float yRelative = y - unit.y;
        float xRelative = x - unit.x;
        float absX = Math.abs(xRelative);
        float absY = Math.abs(yRelative);
        if ((absX < AGR_DISTANCE && absY < AGR_DISTANCE) || isAgred) {
            if (absX < 32 && absY < 32) {
                stopMove();
                attack(unit);
            } else {
                moveTo(-xRelative, -yRelative);
            }
            updatePosWithCheckCollisions();
            //updatePosition();
            isAgred = true;
            return true;
        }
        return false;
    }

    private void attack(MyUnit unit) {
        if (lastAttackTime == 0 || System.currentTimeMillis() - lastAttackTime > ATTACK_DELAY) {
            int damage = (int) Math.floor(this.damage - (this.damage * MyUnit.RANDOM_PART_DAMAGE * Math.random()));
            unit.hitpoint -= damage;
            getStage().addActor(new DamageLabel(unit.x, unit.y, damage));
            lastAttackTime = System.currentTimeMillis();
        }
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
            divider = speed / tmpX;
        } else {
            divider = speed / tmpY;
        }
        velocity[0] = (short) (toX * divider);
        velocity[1] = (short) (toY * divider);
    }

    public void fireBallHit(FireBall fireBall) {
        double damageTmp = (fireBall.DAMAGE - (fireBall.DAMAGE * MyUnit.RANDOM_PART_DAMAGE * Math.random())) * this.damageMultipliyer;
        int damage = (int) Math.floor(damageTmp);
        hitpoint -= damage;
        getStage().addActor(new DamageLabel(this.x, this.y, damage));
        if (hitpoint <= 0) {
            ((MyStage) getStage()).getPlayer().addKillCount();
            if (this instanceof StaticObject) {
                ((StaticObject)this).destroy();
            }
            //    List<MyUnit> units = ((MyStage) getStage()).getUnits();
            //     units.remove(this);
            //     this.remove();
        } else {
            isAgred = true;
            speed = AGR_SPEED;
        }
    }


    @Override
    public void remove() {
        super.remove();
    }

    protected void updatePosWithCheckCollisions() {
        float xTmp = x + velocity[0];
        float yTmp = y + velocity[1];
        boolean missX = false;
        boolean missY = false;
        for (MyUnit myUnit : ((MyStage) stage).getStaticObjects()) {
            if (!missX && Math.abs(myUnit.x - xTmp) < COLLISIONS_WIDTH && Math.abs(myUnit.y - y) < COLLISIONS_WIDTH) {
                missX = true;
            }
            if (!missY && Math.abs(myUnit.y - yTmp) < COLLISIONS_WIDTH && Math.abs(myUnit.x - x) < COLLISIONS_WIDTH) {
                missY = true;
            }
          //  System.out.println(" " + missX + " " + missY);
            if (missX && missY) {
                return;
            }
        }

        if (!missX) {
            x = xTmp;
        }
        if (!missY) {
            y = yTmp;
        }
    }
}
