package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {

    private Pong pong;

    public int paddleNum;
    public int x, y, width = 50, height = 200;
    private int speed;

    public Paddle(Pong pong, int paddleNum) {
        this.paddleNum = paddleNum;
        this.pong = pong;
        if (paddleNum == 1) {
            x = 0;
        } else {
            x = pong.width - width;
        }
        y = pong.height / 2 - height / 2;
    }

    public void move(boolean up) {
        if (pong.bot && paddleNum == 2) {
            speed = 20;
        } else {
            speed = 15;
        }

        if (up) {
            if (y - speed >= 0) {
                y -= speed;
            } else {
                y = 0;
            }
        } else {
            if (y + height + speed <= pong.height) {
                y += speed;
            } else {
                y = pong.height - height;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

}