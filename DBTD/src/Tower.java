
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public interface Tower {
    public void update(ArrayList<Enemy> enemyList);
    public int support();
    public void attack(int x, int y);
    public void upgradeTower();
    public int upgradePrice();
    public void render(Graphics g);
    public void setX(int x);
    public void setY(int y);
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public ArrayList<Projectile> getProjList();
    public boolean findNewTar(ArrayList<Enemy> enemyList, int k);
    public boolean getSpt();
    public boolean getAtk();
    public int getDmg();
    public void removeProj(int i);
    public void clearProjList();
    public String upgradeName();
    public Rectangle getBounds();
    public Rectangle getRange();
}
