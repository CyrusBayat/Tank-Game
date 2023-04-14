package tankGame.control;



import tankGame.filed.GameFiled;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {
    static Map<String, BufferedImage> images = new HashMap<>();
    static Map<String, Clip>sounds=new HashMap<>();
    static Map<String, List<BufferedImage>>animations = new HashMap();




    public static BufferedImage getImage(String key){
        return Resources.images.get(key);
    }

    public static Clip getSound(String key){
        return Resources.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key){
        return Resources.animations.get(key);
    }


    public static void initImages(){
        try {
            Resources.images.put("background",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("title/title.png"),"Could not find title/title.png")));
            Resources.images.put("end1Background",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("title/End1.png"),"Could not find title/End1.png")));
            Resources.images.put("end2Background",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("title/End2.png"),"Could not find title/End2.png")));
            Resources.images.put("tankOne",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("tank/tank1.png"),"Could not find tank1.png")));
            Resources.images.put("tankTwo",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("tank/tank2.png"),"Could not find tank2.png")));
            Resources.images.put("floor",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("floor/floor.jpg"),"Could not find floor/floor.bmp")));
            Resources.images.put("eagle",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("powerUp/eagle.png"),"Could not find powerUp/eagle")));
            Resources.images.put("bullet",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("bullet/Bullet.png"),"Could not find bullet/bullet.jpg")));
            Resources.images.put("unBreak",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("walls/unBreak.jpg"),"walls/unbreak.jpg")));
            Resources.images.put("break1",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("walls/break1.jpg"),"walls/break1.jpg")));
            Resources.images.put("break2",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("walls/break2.jpg"),"walls/break2.jpg")));
            Resources.images.put("speed",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("powerUp/speed.png"),"powerUp/speed.png")));
            Resources.images.put("gun",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("powerUp/gun.png"),"powerUp/gun.png")));
            Resources.images.put("health",ImageIO.read(Objects.requireNonNull(GameFiled.class.getClassLoader().getResource("powerUp/health.png"),"powerUp/health.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }



    }
    public static void initSounds(){
        try {
            AudioInputStream as;
            Clip clip;

            as= AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/bullet.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("bullet",clip);

            as= AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/Music.mid"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("music",clip);

            as= AudioSystem.getAudioInputStream(Resources.class.getClassLoader().getResource("sounds/explosion.wav"));
            clip = AudioSystem.getClip();
            clip.open(as);
            Resources.sounds.put("explosion",clip);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }

    public static void initAnimations(){


        try{
            String baseName = "expl_01_%04d.png";
            List<BufferedImage>temp = new ArrayList<>();

            for (int i = 0; i <24 ; i++) {
                String fName = String.format(baseName, i);

                String fullPath = "explosion3/" + fName;
                temp.add(ImageIO.read(GameFiled.class.getClassLoader().getResource(fullPath)));
            }
            Resources.animations.put("sBullet",temp);

            baseName = "expl_09_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i <32 ; i++) {
                String fName = String.format(baseName, i);

                String fullPath = "explosion1/" + fName;
                temp.add(ImageIO.read(GameFiled.class.getClassLoader().getResource(fullPath)));
            }
            Resources.animations.put("expo",temp);

            baseName = "expl_06_%04d.png";
            temp = new ArrayList<>();
            for (int i = 0; i <32 ; i++) {
                String fName = String.format(baseName, i);

                String fullPath = "explosion2/" + fName;
                temp.add(ImageIO.read(GameFiled.class.getClassLoader().getResource(fullPath)));
            }
            Resources.animations.put("nuke",temp);
        }catch(IOException e){
            System.out.println(e);
            System.exit(-2);
        }


    }
//
//    public static void main(String []args){
//
//        Resources.initImages();
//        Resources.initAnimations();
//        Resources.initSounds();
//    }
}
