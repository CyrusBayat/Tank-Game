package tankGame.stationary.wall;

import java.awt.image.BufferedImage;

public class UnBreakable extends Wall{

    public UnBreakable(float y, float x, BufferedImage img) {
        super(y, x, img,gObjs.UNBREAKABLE);
    }
}
