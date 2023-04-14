package tankGame.control;


import tankGame.GameObject;
import tankGame.moveableObject.Movable;

public class Collide {

    public static boolean check(Movable obj1, GameObject obj2){
        if(obj1.getBoxHit().intersects(obj2.getBoxHit())){

            obj1.collideWith(obj2);
            return true;
        }
        return false;
    }

}
