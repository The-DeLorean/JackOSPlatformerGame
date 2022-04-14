import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.text.*;
import java.io.*;

public class PlatformerPanel extends JPanel
{
   private ArrayList<ArrayList<GameObject>> map = new ArrayList<ArrayList<GameObject>>();
   private int N, numRows, numCol, victoryX, victoryY, startX, startY;
   private double jump;
   private Player player1;
   private int tick = 0;
   
   JFrame startFrame, victoryFrame, deathFrame;

   private boolean down, left, right, up;
   private boolean first = true;
   private boolean once = true;
   private VictoryBlock win;
   
   public PlatformerPanel()
   {
      super();

      //setup
      addKeyListener(new KeyEventDemo());
      setBackground(new Color(0,75,75));
      
      //user input scanner
      Scanner keyboard = new Scanner(System.in);
      
      //file scanner
      Scanner scan = null;
      try{
      scan = new Scanner(new File("Map.txt"));
      }
      catch(FileNotFoundException fnfe){}
      
      //read player start coordinates and size of map
      startX = scan.nextInt();
      startY = scan.nextInt();
      numRows = scan.nextInt();
      numCol = scan.nextInt();
      
      //set up 2D arraylist
      for(int i=0;i<numRows;i++)
      {
         ArrayList<GameObject> innerList = new ArrayList<GameObject>();
         map.add(innerList);
      }
      
      //read in map data with while loop
      for(int i=0; i<numRows; i++)
      {
         for(int j=0; j<numCol; j++)
         {
            int nextType = scan.nextInt();
            
            GameObject nextPiece = null;//temp piece
            
            //determine what object to add
            if(nextType == 0)
               nextPiece = null;
            if(nextType == 1)
               nextPiece = new Block((25*j)+12,(25*i)+12,new Color(0,75,75));
            if(nextType == 2)
            {
               nextPiece = new VictoryBlock((25*j)+12,(25*i)+12,new Color(255,204,51));
               win = (VictoryBlock)nextPiece;
            }
            if(nextType == 3)
               nextPiece = new SpikeTrap((25*j)+12,(25*i)+12, new Color(163, 8, 8));
               
            if(nextType == 4)
               nextPiece = new KeyBlock((25*j)+12,(25*i)+12,new Color(153,204,255));
            
            //add to map
            map.get(i).add(nextPiece);
         }
      }
      //initialize player
      player1 = new Player(25*startX+12, 25*startY+12, Color.BLUE);
      
      //set up timer
      Timer time = new Timer(10, new ButtonListener());
      time.start();
      
      setPreferredSize(new Dimension(numCol*25,numRows*25));//size of panel will change with changing text file
      

   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      //draw background lines
      for(int i=0; i<numRows; i++)
      {
         for(int j=0; j<numCol; j++)
         {
            g.setColor(new Color(0,150,204));
            g.fillRect((25*j)+11,(25*i)+11,24,24);
         }
      }
      
      //draw map objects (blocks/spikes/star/powerup) 
      for(int i=0; i<numRows; i++)
      {
         for(int j=0; j<numCol; j++)
         {
            if(!(map.get(i).get(j) == null)) //if an index is null, nothing will be draw in that 25x25 area
            {
               map.get(i).get(j).draw(g);
            }
         }
      }
      //draw player
      player1.draw(g);
   }
   
   public class KeyEventDemo implements KeyListener
   {
      public void keyTyped(KeyEvent e)//override method
      {
      }
      
      public void keyReleased(KeyEvent e)
      {
      //depending on what keys are pressed/depressed, update booleans
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
         }
         if(e.getKeyCode() == KeyEvent.VK_S)
         {
         }
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = false;
         }
         repaint(); //redraw the GUI to refresh the drawing animation
      }
      
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            //if player is on ground, and w is pressed, jump
            if(player1.isOnGround(map))
            {
               jump = 7;
               N=1;
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_S)
         {
         }
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = true;
         }
         repaint();
      }
   }
   
   public class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         //changes colors of spikes
         if(player1.hasKey() == true && once == true)
         {
            for(int i=0; i<map.size(); i++)
            {
               for(int j=0; j<map.get(i).size(); j++)
               {
                  if(map.get(i).get(j) instanceof SpikeTrap)
                  {
                     map.get(i).get(j).setColor(new Color(153,204,255));
                  }
               }
            }
            once = false;
         }            

         
         //startup window
         if (first == true)
         {
            startFrame = new JFrame();  
            JOptionPane.showMessageDialog(deathFrame,"Collect the powerup to reach the star, but don't hit the spikes!");
            first = false;
         }
         
         //when playe is on ground, gravity and timer tick count will reset
         if(player1.isOnGround(map) == true)
         {
            N = 1;
            tick = 0;
         }
         
         //if player is in the air
         if (player1.isOnGround(map) == false)
         {
            //if player hits ceiling jump will reset
            if(player1.hitsCeiling(map) == true)
            {
               jump = 0;
            }
            
            //gravity moves player down N*1 pixels
            for(int i=0; i<N; i++)
            {
               player1.move(0, 1, map);
            }
         }
         
         
         if(jump>0)
         {
            //player will move up less as jump decreases
            for(int i=0; i<jump; i++)
            {
               player1.move(0, -1, map);
            }
            jump -= 0.1;
         }
         
         if (left)//move 1 pixel left
         {
            player1.move(-1,0,map);
         }
         
         if (right)//move 1 pixel right
         {
            player1.move(1,0,map);
         }
         
         //timer "tick" actions
         if (tick == 20)
         {
            tick = 1;
            
            if(N<8)//will increase while less than 8
               N++;
         }
         else
         {
            tick++;
         }
         
         
         //victory
         if (player1.won() == true)
         {
            //change color of player to gold
            player1.setColor(new Color(255,204,51));
            repaint();
            //victorypanel 
            victoryFrame = new JFrame();  
            JOptionPane.showMessageDialog(victoryFrame,"You Win!");
            System.exit(1);
         }
         
         if (player1.died() == true)
         {
            //that's a lot of damage
            player1.setColor(Color.RED);
            repaint();
            
            //death message
            deathFrame = new JFrame();  
            JOptionPane.showMessageDialog(deathFrame,"YOU DIED LOL");
            
            //reset player and game dynamics
            player1 = new Player(25*startX+12, 25*startY+12, Color.BLUE);
            jump = 0;
            N = 1;
            left = false;
            right = false;
         }
         repaint();
      }
   }
   
}