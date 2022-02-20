import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Cell {
    int type;
    Color color;
    public Cell(int type, Color color)
    {
        this.type = type;
        this.color = color;
    }
    public int getType()
    {
        return type;
    }
    public Color getColor()
    {
        return color;
    }
    public void setType(int type)
    {
        this.type = type;
    }
}
