package tankGame.stationary;


import tankGame.GameObject;
import tankGame.stationary.wall.Wall;

import java.awt.image.BufferedImage;

public class PowerUp extends Wall {
    public static enum Power {
        health, gun, speed, eagle
    }

    public Power type;

    public PowerUp(float y, float x, BufferedImage img, Power type) {
        super(y, x, img, GameObject.gObjs.POWER);
        this.type = type;
    }


}
