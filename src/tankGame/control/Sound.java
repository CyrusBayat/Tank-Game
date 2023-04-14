package tankGame.control;

import javax.sound.sampled.Clip;

public class Sound implements Runnable{

    Clip c;
   public Sound(Clip c){
       this.c=c;
   }

//   public void stopSound(){
//    if(c.isRunning()){
//        c.stop();
//    }
//   }

   public void playSound(){
       if(c.isRunning()){
           c.stop();
       }
       c.setFramePosition(0);
       c.start();
   }

    @Override
    public void run() {

      c.loop(Clip.LOOP_CONTINUOUSLY);

       this.playSound();
       while (true){
           if(Thread.currentThread().isInterrupted()){
               c.stop();
               return;
           }
       }
    }
}
