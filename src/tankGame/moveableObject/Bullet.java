package tankGame.moveableObject;


import java.awt.image.BufferedImage;

public class Bullet extends Movable {

    private float angle;
    private float move = 2.5f;
    private BufferedImage image;

    float scaleFactor = 1f;
    protected final static int DAMAGE = 10;

    public Bullet(float x, float y, float angle, float charge, BufferedImage image) {
        super(y, x, image, gObjs.BULLET);
        this.angle = angle;
        this.scaleFactor = charge;
    }

    public void update() {
        forwardMove();
    }


    protected void forwardMove() {
        x += Math.round(move * Math.cos(Math.toRadians(angle)));
        y += Math.round(move * Math.sin(Math.toRadians(angle)));
        this.boxHit.setLocation((int) x, (int) y);
    }


}


