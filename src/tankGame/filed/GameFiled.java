package tankGame.filed;


import tankGame.control.Collide;
import tankGame.control.Resources;
import tankGame.control.Sound;
import tankGame.control.TankControl;
import tankGame.Launcher;
import tankGame.moveableObject.Tank;
import tankGame.stationary.PowerUp;
import tankGame.stationary.wall.Breakable;
import tankGame.stationary.wall.UnBreakable;
import tankGame.stationary.wall.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameFiled extends JPanel implements Runnable {

    private Tank t1;
    private Tank t2;
    private BufferedImage pic;

    private long tick = 0;
    List<Wall> gObj = new ArrayList<>();
    private Launcher launcher;
    Sound c;

    public GameFiled(Launcher launcher) {
        this.launcher = launcher;

    }


    @Override
    public void run() {


        try {

            this.rest();
            Thread t = new Thread(new Sound(Resources.getSound("music")));
            t.start();
            while (true) {
                this.tick++;
                this.t1.update();
                this.t2.update();


                for (int i = 0; i < gObj.size(); i++) {
                    Collide.check(t1, gObj.get(i));
                    Collide.check(t2, gObj.get(i));

                }

                int numOfBullet1 = t1.getBullet().size();
                int numOfBullet2 = t2.getBullet().size();
                for (int i = 0; i < Math.max(numOfBullet1, numOfBullet2); i++) {
                    for (int s = 0; s < gObj.size(); s++) {
                        if (i < numOfBullet1)
                            Collide.check(t1.getBullet().get(i), gObj.get(s));
                        if (i < numOfBullet2)
                            Collide.check(t2.getBullet().get(i), gObj.get(s));

                        if (!gObj.get(s).toShow()) { //once an object is shot at and isShow() os false, the object will no longer be drawn
                            gObj.remove(s);
                        }
                    }
                    //Check t1's bullets and t2 collision
                    if (i < numOfBullet1)
                        Collide.check(t1.getBullet().get(i), t2);
                    if (i < numOfBullet2)
                        Collide.check(t2.getBullet().get(i), t1);
                }
                Collide.check(t1, t2);
                Collide.check(t2, t1);


                this.repaint();


                Thread.sleep(1000 / 144);

                if (t1.dead()) {
                    t.interrupt();
                    this.launcher.setFrame("end1");

                    return;
                }
                if (t2.dead()) {
                    t.interrupt();
                    this.launcher.setFrame("end2");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }


    //tank position on board after reset
    private void rest() {
        this.tick = 0;
        this.t1.setPosition(30, 30);
        this.t2.setPosition(1830, 1350);
    }

    /**
     * @author anthony-pc
     */
    public void Initialize() {
        Resources.initImages();
        Resources.initSounds();
        Resources.initAnimations();

        this.pic = new BufferedImage(1920, 1440, BufferedImage.TYPE_INT_RGB);


        t1 = new Tank(30, 30, 0, 0, (short) 0, Resources.getImage("tankOne"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.launcher.getFrame().addKeyListener(tc1);
        t2 = new Tank(1830, 1350, 0, 0, (short) 0, Resources.getImage("tankTwo"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.launcher.getFrame().addKeyListener(tc2);

        //Map
        try (BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameFiled.class.getClassLoader().getResourceAsStream("map/Map.txt")))) {
            String[] size = mapReader.readLine().split(",");
//            int numberOfRows = Integer.parseInt(size[0]);
//            int numberOfColumns= Integer.parseInt(size[1]);
            for (int i = 0; mapReader.ready(); i++) {
                String[] items = mapReader.readLine().split("");
                for (int j = 0; j < items.length; j++) {
                    switch (items[j]) {
                        case "9" -> {
                            this.gObj.add(new UnBreakable(i * 30, j * 30, Resources.getImage("unBreak")));
                        }
                        case "2" -> {
                            this.gObj.add(new Breakable(i * 30, j * 30, Resources.getImage("break1")));

                        }
                        case "3" -> {
                            this.gObj.add(new Breakable(i * 30, j * 30, Resources.getImage("break2")));

                        }
                        case "5" -> {
                            this.gObj.add(new PowerUp(i * 30, j * 30, Resources.getImage("eagle"), PowerUp.Power.eagle));

                        }
                        case "6" -> {
                            this.gObj.add(new PowerUp(i * 30, j * 30, Resources.getImage("speed"), PowerUp.Power.speed));

                        }
                        case "7" -> {
                            this.gObj.add(new PowerUp(i * 30, j * 30, Resources.getImage("gun"), PowerUp.Power.gun));

                        }
                        case "8" -> {
                            this.gObj.add(new PowerUp(i * 30, j * 30, Resources.getImage("health"), PowerUp.Power.health));

                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = pic.createGraphics();

        //floor
        floor(buffer);

        //wall,powerUp
        for (int i = 0; i < gObj.size(); i++) {
            gObj.get(i).drawImage(buffer);
        }


        //getting the tank
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        //Split Screen
        SpiltScreen(g2, pic);

        //MiniMap
        miniMap(g2, buffer);

        //lives panel
        lifeBar(g2);

        //health panel
        healthBar(g2);

    }

    //To show floor
    private void floor(Graphics2D buffer) {
        for (int i = 0; i < 1920; i += 500) {
            for (int j = 0; j < 1440; j += 450) {
                buffer.drawImage(Resources.getImage("floor"), i, j, null);
            }
        }
    }

    //Split Screen
    private void SpiltScreen(Graphics2D g, BufferedImage pic) {
        BufferedImage leftHalf = pic.getSubimage((int) t1.getScreen_X(), (int) t1.getScreen_y(), (1280 / 2) - 20, 960);
        g.drawImage(leftHalf, 0, 0, null); // draw buffered image to screen
        BufferedImage rightHalf = pic.getSubimage((int) t2.getScreen_X(), (int) t2.getScreen_y(), (1280 / 2), 960);
        g.drawImage(rightHalf, (1280 / 2) - 15, 0, null); // draw buffered image to screen
    }

    //To health number on the screen
    private void healthBar(Graphics2D g2) {
        // tank 1 health panel
        if (this.t1.getHealth() >= 70) g2.setColor(Color.GREEN);
        else if (this.t1.getHealth() >= 45) g2.setColor(Color.YELLOW);
        else g2.setColor(Color.RED);
        g2.setFont(new Font("Courier", Font.BOLD, 150));
        g2.drawString("" + this.t1.getHealth(), 50, 240);
        // tank 2 health panel
        if (this.t2.getHealth() >= 70) g2.setColor(Color.GREEN);
        else if (this.t2.getHealth() >= 45) g2.setColor(Color.YELLOW);
        else g2.setColor(Color.RED);
        g2.setFont(new Font("Courier", Font.BOLD, 150));
        g2.drawString("" + this.t2.getHealth(), 3850, 240);
    }

    //To lives number on the screen
    private void lifeBar(Graphics2D g2) {
        // tank 1 lives panel
        g2.setColor(Color.red);
        for (int i = 0; i < this.t1.getLives(); i++) {
            g2.drawOval(50 + (i * 120), 10, 100, 100);
            g2.fillOval(50 + (i * 120), 10, 100, 100);
        }
        // tank 2 lives panel
        g2.setColor(Color.blue);
        for (int i = 0; i < t2.getLives(); i++) {
            g2.drawOval(3880 + (i * 120), 10, 100, 100);
            g2.fillOval(3880 + (i * 120), 10, 100, 100);
        }
    }

    //To show MiniMap on the screen
    private void miniMap(Graphics2D g2, Graphics2D buffer) {
        BufferedImage miniMap = pic.getSubimage(0, 0, 1920, 1440);
        buffer.setColor(Color.black);
        g2.scale(.165, .165);
        g2.drawImage(miniMap, 2850, 4350, null);
    }

}
