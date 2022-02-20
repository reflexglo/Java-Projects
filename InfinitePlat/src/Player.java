
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

public class Player {

    int x;
    int y;
    Color color;
    int height;
    int width;
    double vx;
    double vy;
    int health;

    public Player(int x, int y, int width, int height, double vx, double vy, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.vx = vx;
        this.vy = vy;
        this.health = 3;
    }

    public void render(Graphics g) {
        g.drawRect(x, y, width, height);
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Player getLeft() {
        return new Player(x, y + 15, 20, 20, 0, 0, Color.BLACK);
    }

    public Player getRight() {
        return new Player(x + width - 20, y + 15, 20, 20, 0, 0, Color.BLACK);
    }

    public Player getJLeft() {
        return new Player(x - 10, y + 15, 20, 20, 0, 0, Color.BLACK);
    }

    public Player getJRight() {
        return new Player(x + width - 10, y + 15, 20, 20, 0, 0, Color.BLACK);
    }

    public Player getTop() {
        return new Player(x + 15, y, 20, 20, 0, 0, Color.BLACK);
    }

    public Player getBottom() {
        return new Player(x + 15, y + height-10, 20, 20, 0, 0, Color.BLACK);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
