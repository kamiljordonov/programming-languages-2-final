import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGame extends JPanel implements KeyListener, ActionListener {
    private boolean isPlaying = false;
    private int totalScore = 0;
    private int totalBricks = 21;
    private final int jFrameWidth;
    private final int jFrameHeight;

    private final Timer timer;
    private final int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdirection = -1;
    private int ballYdirection = -2;

    private MapGenerator mapGenerator;

    PlayGame(int width, int height) {
        jFrameWidth = width;
        jFrameHeight = height;
        mapGenerator = new MapGenerator(3, 12, width, height);
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.black));
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, jFrameWidth - 8, jFrameHeight - 8);

        // draw map
        mapGenerator.draw((Graphics2D)g);

        // borders
        g.setColor(Color.red);
        g.fillRect(0, 0, 3, jFrameHeight - 8);
        g.fillRect(0, 0, jFrameWidth - 8, 3);
        g.fillRect(jFrameWidth - 9, 0, 3, jFrameHeight - 8);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, jFrameHeight - 122, 100, 8);

        // the ball
        g.setColor(Color.red);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        // scores
        g.setColor(Color.blue);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + totalScore, jFrameHeight / 2 - 10, 30);

        // restart and game over
        if (ballPositionY > jFrameHeight - 30) {
            isPlaying = false;
            ballXdirection = 0;
            ballYdirection = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Gamer Over! Score: " + totalScore, jFrameWidth / 2 - 120, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart...", jFrameWidth / 2 - 80, 350);
        }

        if (totalBricks <= 0) {
            isPlaying = false;
            ballXdirection = 0;
            ballYdirection = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You won!", 260, jFrameWidth / 2 - 100);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Restart game? Press Enter!", 230, jFrameWidth / 2 - 50);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (isPlaying) {
            if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, jFrameHeight - 122, 100, 8))) {
                ballYdirection = -ballYdirection;
            }

            A:
            for (int i = 0; i < mapGenerator.map.length; i++) {
                for (int j = 0; j < mapGenerator.map[i].length; j++) {
                    if (mapGenerator.map[i][j] > 0) {
                        int brickX = j * mapGenerator.brickWidth+ 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks--;
                            totalScore += 5;

                            if (ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width) {
                                ballXdirection = -ballXdirection;
                            } else {
                                ballYdirection = -ballYdirection;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPositionX += ballXdirection;
            ballPositionY += ballYdirection;

            if (ballPositionX < 0) {
                ballXdirection = -ballXdirection;
            }

            if (ballPositionY < 0) {
                ballYdirection = -ballYdirection;
            }

            if (ballPositionX > jFrameWidth - 30) {
                ballXdirection = - ballXdirection;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= jFrameWidth - 100) {
                playerX = jFrameWidth - 100;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!isPlaying) {
                isPlaying = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXdirection = -1;
                ballYdirection = -2;
                playerX = 310;
                totalScore = 0;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3, 7, jFrameWidth, jFrameHeight);
                repaint();
            }
        }
    }

    public  void moveRight() {
        isPlaying = true;
        playerX += 20;
    }

    public  void moveLeft() {
        isPlaying = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
