import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import javafx.util.Pair;
public class Client extends JPanel implements ActionListener
{

    Timer clock;
    Tile[][] board;
    int money;
    
    Client(){
        money = 0;
        board = new Tile[4][4];
        for(int i = 0;i<4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                board[i][j] = new Tile(900+65*j,500+65*i,60,60,0,Color.GRAY);
            }
        }
        int randX = (int)(Math.random()*4);
        int randY = (int)(Math.random()*4);
        int randNum = (int)(Math.random()*2);
        if(randNum == 0)
        {
            board[randX][randY].setNum(2);
        }
        else
        {
            board[randX][randY].setNum(4);
        }
        
        int xDim = 2560;
        int yDim = 1440;
        clock = new Timer(10,this);    
        clock.start(); 
        Dimension dim = new Dimension(xDim,yDim);        
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setFocusable(true);
    }
    
    public void drawBoard(Graphics g)
    {
        for(int i = 0;i<4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                board[i][j].render(g);
            }
        }
    }
    
    public void drawMoney(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Money: "+money, 100, 100);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBoard(g);
        drawMoney(g);
        repaint();
    }
    
    
   public void addNotify(){
       super.addNotify();
         addKeyListener(new KeyHandler());
         addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseHandler());
   }
   
   public void incNums()
   {
       for(int i = 0;i<4;i++)
       {
           for(int j = 0;j<4;j++)
           {
               board[i][j].setNum(board[i][j].getNum()*2);
           }
       }
   }
   
   public int slideUp()
   {
       int cnt = 0;
       for(int j = 0;j<4;j++)
       {
           for(int i = 0;i<4;i++)
           {
               if(board[i][j].getNum() != 0)
               {
               int ind = i-1;
               while(ind >= 0)
               {
                   if(board[ind][j].getNum() == 0)
                   {
                       ind--;
                   }
                   else
                   {
                       if(board[ind][j].getNum() == board[i][j].getNum())
                       {
                           money += board[i][j].getNum();
                           board[ind][j].setNum(board[ind][j].getNum()*2);
                           board[i][j].setNum(0);
                           break;
                       }
                       else
                       {
                            int temp = board[i][j].getNum();
                            board[i][j].setNum(0);
                            board[ind+1][j].setNum(temp);
                            break;
                       }
                   }
               }
               if(ind != i-1 || board[i][j].getNum() == 0)
               {
                   cnt++;
               }
               if(ind == -1)
               {
                   int temp = board[i][j].getNum();
                   board[i][j].setNum(0);
                   board[ind+1][j].setNum(temp);
                   
               }
               }
           }
       }
       return cnt;
   }
   
   public int slideDown()
   {
       int cnt = 0;
       for(int j = 0;j<4;j++)
       {
           for(int i = 3;i>=0;i--)
           {
               if(board[i][j].getNum() != 0)
               {
               int ind = i+1;
               while(ind < 4)
               {
                   if(board[ind][j].getNum() == 0)
                   {
                       ind++;
                   }
                   else
                   {
                       if(board[ind][j].getNum() == board[i][j].getNum())
                       {
                           money += board[i][j].getNum();
                           board[ind][j].setNum(board[ind][j].getNum()*2);
                           board[i][j].setNum(0);
                           break;
                       }
                       else
                       {
                           int temp = board[i][j].getNum();
                           board[i][j].setNum(0);
                           board[ind-1][j].setNum(temp);
                           break;
                       }
                   }
               }
               if(ind != i+1 || board[i][j].getNum() == 0)
               {
                   cnt++;
               }
               if(ind == 4)
               {
                   int temp = board[i][j].getNum();
                   board[i][j].setNum(0);
                   board[ind-1][j].setNum(temp);
                   
               }
               }
           }
       }
       return cnt;
   }
   
   public int slideRight()
   {
       int cnt = 0;
       for(int i = 0;i<4;i++)
       {
           for(int j = 3;j>=0;j--)
           {
               if(board[i][j].getNum() != 0)
               {
               int ind = j+1;
               while(ind < 4)
               {
                   if(board[i][ind].getNum() == 0)
                   {
                       ind++;
                   }
                   else
                   {
                       if(board[i][ind].getNum() == board[i][j].getNum())
                       {
                           money += board[i][j].getNum();
                           board[i][ind].setNum(board[i][ind].getNum()*2);
                           board[i][j].setNum(0);
                           break;
                       }
                       else
                       {
                            int temp = board[i][j].getNum();
                            board[i][j].setNum(0);
                            board[i][ind-1].setNum(temp);
                            break;
                       }
                   }
               }
               if(ind != j+1 || board[i][j].getNum() == 0)
               {
                   cnt++;
               }
               if(ind == 4)
               {
                   int temp = board[i][j].getNum();
                   board[i][j].setNum(0);
                   board[i][ind-1].setNum(temp);
               }
               }
           }
       }
       return cnt;
   }
   
   public int slideLeft()
   {
       int cnt = 0;
       for(int i = 0;i<4;i++)
       {
           for(int j = 0;j<4;j++)
           {
               if(board[i][j].getNum() != 0)
               {
                    int ind = j-1;
                    while(ind >= 0)
                    {
                        if(board[i][ind].getNum() == 0)
                        {
                            ind--;
                        }
                        else
                        {
                            if(board[i][ind].getNum() == board[i][j].getNum())
                            {
                                money += board[i][j].getNum();
                                board[i][ind].setNum(board[i][ind].getNum()*2);
                                board[i][j].setNum(0);
                                break;
                            }
                            else
                            {
                                 int temp = board[i][j].getNum();
                                 board[i][j].setNum(0);
                                 board[i][ind+1].setNum(temp);
                                 break;
                            }
                        }
                    }

                    if(ind != j-1 || board[i][j].getNum() == 0)
                    {
                        cnt++;
                    }
                    if(ind == -1)
                    {
                        int temp = board[i][j].getNum();
                        board[i][j].setNum(0);
                        board[i][ind+1].setNum(temp);
                    }
               }
           }
       }
       return cnt;
   }
   
   public void spawnTile()
   {
       ArrayList<Tile> empty = new ArrayList<Tile>();
       for(int i = 0;i<4;i++)
       {
           for(int j = 0;j<4;j++)
           {
               if(board[i][j].getNum() == 0)
               {
                   empty.add(board[i][j]);
               }
           }
       }
       int randTile = (int)(Math.random()*empty.size());
       int randNum = (int)(Math.random()*2);
       if(randNum == 0)
       {
           empty.get(randTile).setNum(2);
       }
       else
       {
           empty.get(randTile).setNum(4);
       }
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    public class KeyHandler extends KeyAdapter implements KeyListener {
      @Override
      public void keyPressed(KeyEvent e) {        
        String key = KeyEvent.getKeyText(e.getKeyCode());
        
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
          if(key.equals("Up"))
          {
              if(slideUp() > 0)
              {
                spawnTile();
              }
          }
          if(key.equals("Down"))
          {
              if(slideDown() > 0)
              {
                spawnTile();
              }
          }
          if(key.equals("Right"))
          {
              if(slideRight() > 0)
              {
                spawnTile();
              }
          }
          if(key.equals("Left"))
          {
              if(slideLeft() > 0)
              {
                spawnTile();
              }
          }
          if(key.equals("Space") && money > 100)
          {
              incNums();
              money -= 100;
          }
      }
    
     
}
     public class MouseHandler extends MouseAdapter
    {
      @Override
      public void mouseMoved(MouseEvent e){
         
      }
      @Override
      public void mousePressed(MouseEvent e)
      {
         
        
      }
     } 

}
