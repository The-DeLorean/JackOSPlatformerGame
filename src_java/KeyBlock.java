package src_java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*
KeyBlock is a powerup that enables the player to survive spikes
once collected it will dissapear from the board
*/
public class KeyBlock extends GameObject
{
   private boolean collected;
   
   public KeyBlock(int xPos, int yPos, Color myColor)
   {
      super(xPos,yPos,myColor);
   }
   
   public void draw(Graphics g)
   {
      //omce collected will be invisible
      if(collected == false)
      {
         g.setColor(new Color(255,204,51));
         g.fillOval(getX()-5, getY()-5, 10, 10);
      }
   }
   
   public void collect()
   {
      collected = true;
   }
}