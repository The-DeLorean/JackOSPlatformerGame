import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
The victory block is a special type of GameObject, whereupon if it is touched, the game will end.
The victory block is in the shape of a small star, although the hitbox is still a 25x25 square.
*/
public class VictoryBlock extends GameObject
{
   
   public VictoryBlock(int xPos, int yPos, Color myColor)
   {
      super(xPos,yPos,myColor);
   }
   
   public void draw(Graphics g)
   {
      //star polygon
      Polygon star = new Polygon();
      g.setColor(getMyColor());
      star.addPoint(getX(), getY()-12);
      
      star.addPoint(getX()-3, getY()-5);
      star.addPoint(getX()-10, getY()-5);
      star.addPoint(getX()-4, getY());
      star.addPoint(getX()-7, getY()+9);
      
      star.addPoint(getX(), getY()+4);

      star.addPoint(getX()+7, getY()+9);
      star.addPoint(getX()+4, getY());
      star.addPoint(getX()+10, getY()-5);
      star.addPoint(getX()+3, getY()-5);
      
      g.fillPolygon(star);
   }
}