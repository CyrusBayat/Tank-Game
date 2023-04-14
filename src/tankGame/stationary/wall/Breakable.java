package tankGame.stationary.wall;

import java.awt.image.BufferedImage;

public class Breakable extends Wall{
    public Breakable(float y, float x, BufferedImage img) {
        super(y, x, img,gObjs.BREAKABLE);
    }
}
