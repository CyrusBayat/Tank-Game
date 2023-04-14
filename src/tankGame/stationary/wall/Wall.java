package tankGame.stationary.wall;



import tankGame.GameObject;

import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {
    public Wall(float y , float x,BufferedImage img, gObjs id ){
        super(y,x,img,id);
    }

}
