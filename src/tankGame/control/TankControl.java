package tankGame.control;


import tankGame.moveableObject.Tank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author anthony-pc
 */
public class TankControl implements KeyListener {
    private Tank t1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    /**
     * @author anthony-pc
     */
    public TankControl(Tank t1, int up, int down, int left, int right, int shoot) {
        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }
    /**
     * @author anthony-pc
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
    /**
     * @author anthony-pc
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if (keyPressed == up) {
            this.t1.frontMove();
        }
        if (keyPressed == down) {
            this.t1.backMove();
        }
        if (keyPressed == left) {
            this.t1.leftRotate();
        }
        if (keyPressed == right) {
            this.t1.rightRotate();
        }
        if (keyPressed == shoot) {
            this.t1.shootPress();
        }

    }
    /**
     * @author anthony-pc
     */

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();
        if (keyReleased  == up) {
            this.t1.unFrontMove();
        }
        if (keyReleased == down) {
            this.t1.unBackMove();
        }
        if (keyReleased  == left) {
            this.t1.unLeftRotate();
        }
        if (keyReleased  == right) {
            this.t1.unRightRotate();
        }
        if (keyReleased == shoot) {
            this.t1.unShootPress();
        }
    }
}
