package src_java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
A spike trap is a special GameObject shaped like a red triangle (although hitbox is still a 25x25square).
If the player touches it, they will die, lest they have a powerup, in which case the spikes will turn blue.
*/
public class SpikeTrap extends GameObject
{
   
   public SpikeTrap(int xPos, int yPos, Color myColor)
   {
      super(xPos,yPos,myColor);
   }
   
   public void draw(Graphics g)
   {
      //triangle polygon
      Polygon spike = new Polygon();
      g.setColor(getMyColor());
      spike.addPoint(getX()-12, getY()+12);
      spike.addPoint(getX()+12, getY()+12);
      spike.addPoint(getX(), getY()-12);
      
      g.fillPolygon(spike);
   }
}