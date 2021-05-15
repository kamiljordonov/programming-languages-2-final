import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setTitle("Catch Ball");
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setBounds(10, 10, screenSize.width, screenSize.height);
        obj.setVisible(true);
        PlayGame playGame = new PlayGame(obj.getSize().width, obj.getSize().height);
        playGame.setFocusable(true);
        playGame.requestFocusInWindow();
        obj.add(playGame);
    }
}
