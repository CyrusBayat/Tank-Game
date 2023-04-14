package tankGame.moveableObject;


import tankGame.GameObject;
import tankGame.stationary.PowerUp;

import java.awt.image.BufferedImage;


public abstract class Movable extends GameObject {
    protected int oldX;
    protected float move = 2f;
    protected int oldY;
    protected float vx, vy, angle;
    private float rotationS = 2.5f;

    public Movable(float y, float x, BufferedImage img, gObjs id) {
        super(y, x, img, id);
        this.vx = 0;
        this.vy = 0;

        oldX = (int) x;
        oldY = (int) y;

    }


    protected void rotateLeft() {
        this.angle -= this.rotationS;
    }

    protected void rotateRight() {
        this.angle += this.rotationS;
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= 1920 - 88) {
            x = 1920 - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= 1440 - 80) {
            y = 1440 - 80;
        }
    }

    protected void backwardMove() {
        oldX = (int) x;
        oldY = (int) y;
        vx = Math.round(move * Math.cos(Math.toRadians(angle)));
        vy = Math.round(move * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;

        //add boxhit for move
        this.boxHit.setLocation((int) x, (int) y);
        checkBorder();
    }

    protected void forwardMove() {
        oldX = (int) x;
        oldY = (int) y;
        vx = Math.round(move * Math.cos(Math.toRadians(angle)));
        vy = Math.round(move * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        //add boxhit for move
        this.boxHit.setLocation((int) x, (int) y);

    }

    private void setOldPosition() {
        x = oldX;
        y = oldY;
    }

    public void collideWith(GameObject other) {

        switch (this.id) {
            case TANK:
                switch (other.id) {
                    case POWER:
                        ((Tank) this).getPower((PowerUp) other);
                        other.setShow(false);
                        break;
                    case TANK:
                    case BREAKABLE:
                    case UNBREAKABLE:
                        setOldPosition();
                        break;
                }
                break;
            case BULLET:
                switch (other.id) {
                    case TANK:
                        this.setShow(false);
                        ((Tank) other).gotShot(Bullet.DAMAGE);
                        break;
                    case POWER:
                        break;
                    case UNBREAKABLE:
                        this.setShow(false);
                        break;
                    case BREAKABLE:
                        other.setShow(false);
                        this.setShow(false);
                        break;
                }

                break;
        }


    }
}
