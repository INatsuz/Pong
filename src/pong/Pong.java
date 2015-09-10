package pong;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Pong implements ActionListener, KeyListener {

    public static Pong pong;
    private JFrame jFrame;
    private Renderer renderer;
    private Random random;
    private Ball ball;
    private Paddle player1, player2;

    public int width = 700, height = 700;
    public int score1, score2;
    private int botCooldown = 0;
    private int botMoves = 0;

    private boolean running = false;
    private boolean w = false, s = false, up = false, down = false;
    public boolean ballMoving = false;
    public boolean bot;

    public Pong() {
        Timer timer = new Timer(20, this);
        jFrame = new JFrame("Pong");

        renderer = new Renderer();
        random = new Random();

        ball = new Ball(this);

        running = false;
        jFrame.setVisible(true);
        jFrame.setSize(width + 6, height + 28);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.add(renderer);
        jFrame.addKeyListener(this);

        timer.start();
    }

    public static void main(String[] args) {

        pong = new Pong();

    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (running == true) {
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2f));
            g.drawOval(width / 2 - 100, height / 2 - 100, 200, 200);
            g.drawLine(width / 2, 0, width / 2, height);
            g.setFont(new Font("Arial", 1, 60));
            g.drawString(Integer.toString(score1), 50, 85);
            g.drawString(Integer.toString(score2), width - 80, 85);
            ball.render(g);
            player1.render(g);
            player2.render(g);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Pong", width / 2 - 64, height / 2 - 150);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space To Play", width / 2 - 150, height / 2 - 50);
            g.drawString("Press Shift To Play With A Bot", width / 2 - 210, height / 2);
        }
    }
    
    public void start() {
        ball = new Ball(this);
        player1 = new Paddle(this, 1);
        player2 = new Paddle(this, 2);

        running = true;

        botCooldown = 0;
        botMoves = 0;
    }

    public void update() {
        if (score1 >= 5 || score2 >= 5) {
            score1 = 0;
            score2 = 0;
            running = false;
        }
        if (w && ballMoving) {
            player1.move(true);
        }
        if (s && ballMoving) {
            player1.move(false);
        }
        if (up && bot == false && ballMoving) {
            player2.move(true);
        }
        if (down && bot == false && ballMoving) {
            player2.move(false);
        }
        if (bot) {
            if ((player2.y + player2.height / 2) - (ball.y + ball.height / 2) <= -20) {
                player2.move(false);
                botMoves++;
            } else if ((player2.y + player2.height / 2) - (ball.y + ball.height / 2) >= 20) {
                player2.move(true);
                botMoves++;
            }

        }

        ball.update(player1, player2);
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            update();
        }
        renderer.repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) {
            w = true;
        }
        if (id == KeyEvent.VK_S) {
            s = true;
        }
        if (id == KeyEvent.VK_UP) {
            up = true;
        }
        if (id == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (id == KeyEvent.VK_SPACE) {
            if (running == false) {
                bot = false;
                score1 = 0;
                score2 = 0;
                start();
            } else if (ballMoving == false) {
                ballMoving = true;
                if (random.nextBoolean()) {
                    ball.speedX = 1;
                } else {
                    ball.speedX = -1;
                }
                if (random.nextBoolean()) {
                    ball.speedY = 1;
                } else {
                    ball.speedY = -1;
                }
            }
        }
        if (id == KeyEvent.VK_SHIFT) {
            if (!running) {
                bot = true;
                score1 = 0;
                score2 = 0;
                start();
            }
        }
        if (id == KeyEvent.VK_ESCAPE && running == true) {
            running = false;
            ballMoving = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_W) {
            w = false;
        }
        if (id == KeyEvent.VK_S) {
            s = false;
        }
        if (id == KeyEvent.VK_UP) {
            up = false;
        }
        if (id == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

}