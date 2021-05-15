import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setPreferredSize(new Dimension(toolkit.width, toolkit.height));
        obj.setBounds(10, 10, toolkit.width, toolkit.height);
        obj.setTitle("Catch Ball");
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setVisible(true);
        obj.setResizable(false);
        Insets insets = obj.getInsets();
        System.out.println(insets.bottom);
        PlayGame playGame = new PlayGame(toolkit.width, toolkit.height - insets.top);
        playGame.setFocusable(true);
        playGame.requestFocusInWindow();
        obj.add(playGame);
    }
}
