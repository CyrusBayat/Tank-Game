package tankGame.moveableObject;



import tankGame.control.Resources;
import tankGame.control.Sound;
import tankGame.stationary.PowerUp;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tank extends Movable {




    private boolean upPress;
    private boolean downPress;
    private boolean rightPress;
    private boolean leftPress;
    private boolean shootPress;

    private boolean dead;


    Bullet b;
    List<Bullet>bullets = new ArrayList<>();
    List< Animation> animations= new ArrayList<>();

    float fireDelay = 100;
    float coolDown = 0;
    float rateOfFire = 0.8f;
    float charge=1f;
    float chargeRate = 0.05f;
    private int health;
    int life=3;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage image) {
        super(x,y,image,gObjs.TANK);


        health=100;


    }

    public void setPosition(float x,float y){
        this.x = x;
        this.y = y;
        //add boxhit for move
        this.boxHit.setLocation((int)x,(int)y);

    }

//    public float getAngle(){ return angle;}
    public float getX () {return (int)this.x;}
    public float getY () {return this.y;}


    public void setAngle(float angle){this.angle=angle;}

    public void frontMove() {
        this.upPress = true;
    }

    public void backMove() {
        this.downPress = true;
    }

    public void rightRotate() {
        this.rightPress = true;
    }

    public void leftRotate() {
        this.leftPress = true;
    }
    public void shootPress() {this.shootPress=true;}

    public void unFrontMove() {
        this.upPress = false;
    }

    public void unBackMove() {
        this.downPress = false;
    }

    public void unRightRotate() {
        this.rightPress = false;
    }

    public void unLeftRotate() {
        this.leftPress = false;
    }

    public void unShootPress() {
        this.shootPress = false;
    }

    public void getPower(PowerUp p){
        if(p.toShow()){
            switch (p.type){
                case health:
                    addHealth();
                    break;
                case gun:
                    gunPower();
                    break;
                case eagle:
                    addEagle();
                    break;
                case speed:
                    addSpeed();
                    break;
            }

        }
    }
    private void gunPower(){
        if(rateOfFire<=4f)
        rateOfFire =rateOfFire+ 0.5f;
    }
    public void addEagle(){
        if(life<6){
            life=6;
        }
        if(rateOfFire<=6f){
            rateOfFire =rateOfFire+ 5f;
        }
        if(move<=4f){
            move=move+5f;
        }
    }
    public void addSpeed(){
        if(move>=4f) {
            move = move + 0.5f;
        }
    }

    public void addHealth(){
        if(life<=6){
            health = health+10;
            if(health>=100){
                life++;
                health=10;
                if (life == 7) {
                    life=5;
                    health=100;
                }
            }
        }
    }




    public void update() {
        if (this.upPress) {
            this.forwardMove();
        }
        if (this.downPress) {
            this.backwardMove();
        }
        if (this.leftPress) {
            this.rotateLeft();
        }
        if (this.rightPress) {
            this.rotateRight();
        }
        if (this.shootPress && this.coolDown>=this.fireDelay) {
          fire();
        }
        this.coolDown+=this.rateOfFire;



        //bullet
//        this.bullets.forEach(b->b.update());
        for (int i = 0; i < bullets.size(); i++) {
            if(bullets.get(i).toShow()){
                bullets.get(i).update();

            }
            else {
                bullets.remove(i);
                (new Sound(Resources.getSound("explosion"))).playSound();
//                Animation a= new Animation(bullets.size(),bullets.size(),Resources.getAnimation("sBullet"));
//                a.start();
//                animations.add(a);
            }
        }

        //to stop the bullet Animation
//        this.animations.removeIf(a -> !a.isRunning()) ;
        CenterScreen();



    }

    private void fire(){
        this.coolDown=0;
        (new Sound(Resources.getSound("bullet"))).playSound();
        this.bullets.add(new Bullet(this.setBulletAtX(),this.setBulletAtY(),angle, charge,Resources.getImage("bullet")));
        //add the bullet Animation
        Animation a= new Animation(setBulletAtX()-28,setBulletAtY()-28,Resources.getAnimation("sBullet"));
        a.start();
        animations.add(a);
    }



    public List<Bullet>getBullet(){return bullets;}

    private int setBulletAtX(){
        float cx= 29f *( float) Math.cos(Math.toRadians(angle));
        return  (int) x + this.image.getWidth() / 2 + (int) cx - 4;
    }
    private int setBulletAtY(){
        float cy = 29f * (float) Math.sin (Math.toRadians(angle));
        return  (int) y + this.image.getHeight() / 2 + (int) cy - 4;
    }






    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }




    void gotShot(int damage){

        health=health-10;
        if (health <= 0) {
            //and
            if (life > 1) {
                life--;
                health = 100;
            }
            if(life<=0 || health<=0){
                dead=true;
            }
        }

    }





    public boolean dead(){
        return dead;
    }
    public float getHealth () {return this.health;}
    public float getLives () {return this.life;}

    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);

        //Bullet
//         if(b !=null) b.drawImage(g2d);
//        this.bullets.forEach(b -> b.drawImage(g2d));

        for (int i = 0; i <bullets.size() ; i++) {
            bullets.get(i).drawImage(g);
        }

        //tank border
//        g2d.setColor(Color.MAGENTA);
//        g2d.drawRect((int) x, (int) y, this.image.getWidth(), this.image.getHeight());

        //bullet Anim
//        this.animations.forEach(a ->a.drawImage(g2d));
        for (int i = 0; i <animations.size() ; i++) {
            animations.get(i).drawImage(g2d);
        }

    }
    float screen_x = 0;
    float screen_y = 0;
    private void CenterScreen() {
        this.screen_x = (int) this.getX() - 1280 / 4;
        this.screen_y = (int)this.getY()-960/2;
        if(this.screen_x<0){
            this.screen_x=0;
        }
        if(this.screen_y < 0){
            this.screen_y = 0;
        }
        if(this.screen_x > 1920-1280/2){
            this.screen_x = 1920-1280/2;
        }
        if(this.screen_y > 1440-960){
            this.screen_y = 1440-960;
        }
    }

    public float getScreen_X() {
        CenterScreen();
        return screen_x;

    }

    public float getScreen_y() {
        CenterScreen();
        return screen_y;
    }




}
