package tankGame.menu;



import tankGame.control.Resources;
import tankGame.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author anthony-pc
 */
public class StartMenu extends JPanel{
    private JButton start;
    private JButton exit;
    private Launcher launcherP;
    private BufferedImage background;


    /**
     * @author anthony-pc
     */
    public StartMenu(Launcher launcherP){
        this.launcherP=launcherP;
        Resources.initImages();
        background = Resources.getImage("background");

        this.setBackground(Color.black);
        this.setLayout(null);

        // Start Button
        start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(550/3, 300, 130, 50);
        start.addActionListener((actionEvent -> {
            this.launcherP.setFrame("game");
        }));

        //Exit Button
        exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(550/3, 400, 130, 50);
        exit.addActionListener((actionEvent -> {
            this.launcherP.closeGame();
        }));

        //Adding the button to the panel
        this.add(start);
        this.add(exit);

    }
    /**
     * @author anthony-pc
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }

}
