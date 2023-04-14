package tankGame.menu;



import tankGame.control.Resources;
import tankGame.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * @author anthony-pc
 */
public class EndPanel2 extends JPanel {

    private JButton start;
    private JButton exit;
    private Launcher launcherP;
    private BufferedImage background;


    public EndPanel2(Launcher launcherP) {
        this.launcherP = launcherP;

        Resources.initImages();
        background = Resources.getImage("end2Background");

        this.setBackground(Color.black);
        this.setLayout(null);

        // Start Button
        start = new JButton("Menu");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(270, 400, 120, 50);
        start.addActionListener((actionEvent -> {
            this.launcherP.setFrame("start");
        }));

        //Exit Button
        exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(50, 400, 120, 50);
        exit.addActionListener((actionEvent -> {
            this.launcherP.closeGame();
        }));

        //Adding the button to the panel
        this.add(start);
        this.add(exit);

    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);

        g.setColor(Color.red);
        g.setFont(new Font("areal", Font.BOLD, 40));
        g.drawString("Player 2  win!! ", 100,300);
    }


}