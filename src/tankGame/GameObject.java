package tankGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    protected float x, y;
    protected BufferedImage image;
    protected Rectangle boxHit;

    public static enum gObjs {
        TANK, UNBREAKABLE, BREAKABLE, POWER, BULLET
    }

    public gObjs id;

    protected boolean show;

    public GameObject(float y, float x, BufferedImage img, gObjs id) {
        this.y = y;
        this.x = x;
        this.image = img;
        this.boxHit = new Rectangle((int) x, (int) y, this.image.getWidth(), this.image.getHeight());

        this.id = id;
        this.show = true;


    }

    public boolean toShow() {
        return show;
    }

    public void setShow(boolean set) {

        this.show = set;
    }

    public void drawImage(Graphics2D buffer) {
        if (this.show) {
            buffer.drawImage(image, (int) x, (int) y, null);
        }
    }


    public Rectangle getBoxHit() {
        return this.boxHit.getBounds();
    }


}
