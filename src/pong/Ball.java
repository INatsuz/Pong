package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {

    private Pong pong;
    private Random random;

    public int x, y, width = 25, height = 25;
    public int speedX, speedY, baseSpeed = 5, speed;
    private int hits = 5;

    public Ball(Pong pong) {
        this.pong = pong;

        random = new Random();

        start();
    }

    public void update(Paddle paddle1, Paddle paddle2) {
        speed = baseSpeed * hits / 5;
        if(pong.ballMoving == true)
            x += speedX * speed;
        if (y + speedY * speed >= 0 && y + height + speedY * speed <= pong.height) {
            y += speedY * speed;
        } else if (y + speedY * speed < 0) {
            y = 0;
        } else if (y + height + speedY * speed > pong.height) {
            y = pong.height - height;
        }
        if(y <= 0)
            speedY = -speedY;
        else if(y + height >= pong.height)
            speedY = -speedY;
        checkCollision(paddle1);
        checkCollision(paddle2);
        checkScore(paddle1, paddle2);
    }

    public void start() {
        x = pong.width / 2 - width / 2;
        y = pong.height / 2 - height / 2;
        hits = 5;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }

    public void checkCollision(Paddle paddle) {
        //Paddle Collision
        if (paddle.paddleNum == 1) {
            if (x <= paddle.x + paddle.width && y + height >= paddle.y && y <= paddle.y + paddle.height) {
                x = paddle.x + paddle.width;
                speedX = -speedX;
                if(random.nextBoolean())
                    speedY = random.nextInt(2) + 1;
                else
                    speedY = -(random.nextInt(2) + 1);
                hits++;
            }
        } else if (paddle.paddleNum == 2) {
            if (x + width >= paddle.x && y + height >= paddle.y && y <= paddle.y + paddle.height) {
                x = paddle.x - width;
                speedX = -speedX;
                if(random.nextBoolean())
                    speedY = random.nextInt(2) + 1;
                else
                    speedY = -(random.nextInt(2) + 1);
                hits++;
            }
        }
    }

    public void checkScore(Paddle paddle1, Paddle paddle2) {
        //Check if someone scored
        if (x <= 0) {
            pong.score2++;
            pong.start();
            pong.ballMoving = false;
        } else if (x >= pong.width) {
            pong.score1++;
            pong.start();
            pong.ballMoving = false;
        }
    }

}