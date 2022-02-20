import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class ConePanel {
    Polygon poly;
    Color color;
    int prior;
    public ConePanel(Polygon poly, Color color, int prior)
    {
        this.poly = poly;
        this.color = color;
        this.prior = prior;
    }
    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawPolygon(poly);
        g.setColor(color);
        g.fillPolygon(poly);
    }
    public int getPrior()
    {
        return prior;
    }
}
