package tankGame;


import tankGame.filed.GameFiled;
import tankGame.menu.EndPanel1;
import tankGame.menu.EndPanel2;
import tankGame.menu.StartMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Launcher {

    // To store the panel
    private JFrame frame;
    //mainP panel that will let us switch the panel to the sub-panel
    private JPanel mainP;
    //Contain the game start menu
    private JPanel startP;
    //To show the end of the game
    private JPanel endGameP1;
    private JPanel endGameP2;

    //////////////////Use to show the game on the screen
    private GameFiled gameFiled;


    //Using to manage the sub-panels.
    private CardLayout cardLayout;


    public Launcher() {
        this.frame = new JFrame("Tank War");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    private void initializeUI() {
        this.mainP = new JPanel();
        this.startP = new StartMenu(this);
        this.gameFiled = new GameFiled(this);
        this.gameFiled.Initialize();
        this.endGameP1 = new EndPanel2(this);
        this.endGameP2 = new EndPanel1(this);
        cardLayout = new CardLayout();
        this.mainP.setLayout(cardLayout);
        this.mainP.add(startP, "start");

        this.mainP.add(gameFiled, "game");
        this.mainP.add(endGameP1, "end1");
        this.mainP.add(endGameP2, "end2");
        this.frame.add(mainP);
        this.frame.setResizable(false);
        this.setFrame("start");
        // frame location on screen
//        this.frame.setLocationRelativeTo(null);
    }


    public void setFrame(String type) {
        // hiding the frame
        this.frame.setVisible(false);
        switch (type) {
            case "game":
                this.frame.setSize(1280, 995);
                (new Thread(this.gameFiled)).start();
                break;
            case "start":
            case "end1":
            case "end2":
                this.frame.setSize(500, 550);
                break;
        }
        this.cardLayout.show(mainP, type);
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void closeGame() {
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
    }


    public static void main(String[] args) {
//        JOptionPane.showMessageDialog(null, "are you ready to play?");
        Launcher play = new Launcher();
        play.initializeUI();

    }
}
