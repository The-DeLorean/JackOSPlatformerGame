import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameObject
{
   private int xPos, yPos;
   private Color myColor;
   
   public GameObject(int xPos, int yPos, Color myColor)
   {
      this.xPos = xPos;
      this.yPos = yPos;
      this.myColor = myColor;
   }
   
   public boolean collides(GameObject o)
   {
      if(this == o) //objects are same
      {
         return false;
      }
      //objects collide if borders overlap
      else if ((yPos+12 < getY()-12) ||
               (yPos-12 > getY()+12) ||
               (xPos-12 > getX()+12) ||
               (xPos+12 < getX()-12) )  
      {
         return true;
      }
      
      else //they aren't the same and didn't collide
      {
         return true;
      }
   }
   
   public void draw(Graphics g)
   {
      //draw a square
      g.setColor(myColor);
      g.fillRect(xPos-12, yPos-12, 25, 25);
   }
   
   //acessors  and mutators
   public int getX()
   {
      return xPos;
   }
   public int getY()
   {
      return yPos;
   }
   public Color getMyColor()
   {
      return myColor;
   }
   public void incrementX(int dX)
   {
      xPos += dX;
   }
   public void incrementY(int dY)
   {
      yPos += dY;
   }
   
   public void setX(int newX)
   {
      xPos = newX;
   }
   public void setY(int newY)
   {
      yPos = newY;
   }
   public void setColor(Color newColor)
   {
      myColor = newColor;
   }
   public void collect()
   {
   
   }
}