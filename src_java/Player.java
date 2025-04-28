package src_java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;

/*
Player is a special GameObject which is outside of the map Arraylist.
It needs to be able to check whether it is currently colliding with any objects in the map.
I also altered the collisons to implement a death feature, as well as powerup collecting 
and color changing features.
*/

public class Player extends GameObject
{
   private boolean win, death, key;
   
   public Player(int xPos, int yPos, Color myColor)
   {
      super(xPos,yPos,myColor);
   }
   
   //checks to see if the play is current just above another gameobject
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> mapCheck)
   {
      //changes in position
      incrementY(1);
      //tests for collision
      boolean check = collides(mapCheck);
      //reverts change
      incrementY(-1);
      
      if(check == true)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   //similar to isOnGround, will determine if player touches ceiling
   public boolean hitsCeiling(ArrayList<ArrayList<GameObject>> mapCheck)
   {
      incrementY(-1);
      boolean check = collides(mapCheck);
      incrementY(1);
      
      if(check == true)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   //move will move the players position by any delta X and delta Y, that are valid moves
   public boolean move(int dX, int dY, ArrayList<ArrayList<GameObject>> mapCheck)
   {
      //attempts changes in position
      incrementX(dX);
      incrementY(dY);
      
      //if there is a collision, revert changes
      if(collides(mapCheck) == true)
      {
         incrementX(-dX);
         incrementY(-dY);
         return false;
      }
      else//otherwise continue
         return true;
   }
   
   public void draw(Graphics g)
   {
      //draw blue border around black center of square
      g.setColor(getMyColor());
      g.fillRect(getX()-12, getY()-12, 25, 25);
      
      g.setColor(Color.BLACK);
      g.fillRect(getX()-10, getY()-10, 20, 20);
      
      //draw powerup
      if(key == true)
      {
         g.setColor(new Color(255,204,51));
         g.fillOval(getX()-5, getY()-5, 10, 10);
      }
   }
   
   /*This is the collides method of class player, which determines 
   if the player is currently colliding with another GameObject.
   The strategy I implemented is as follows: if the absolute value of the difference 
   between the X and Y positions of the GameObject at index (i,j) in the 2D Arraylist 
   and the player is less than 25, the collides method of class GameObject will be called.
   This way, the method will only be called for nearby blocks, thus not causing a slowdown even for larger maps
   (such as the 30x32 one I used).
   */
   public boolean collides(ArrayList<ArrayList<GameObject>> mapCheck)
   {
      for(int i=0; i<mapCheck.size(); i++)
      {
         for(int j=0; j<mapCheck.get(i).size(); j++)
         {
            if(!(mapCheck.get(i).get(j)==null))//if not null, otherwise will cause exception
            {
               if( ( Math.abs( mapCheck.get(i).get(j).getX() - getX() ) < 25 )
                && ( Math.abs( mapCheck.get(i).get(j).getY() - getY() ) < 25 ) ) //if object is nearby
               {
                  if (mapCheck.get(i).get(j).collides(this))//check for collision
                  {
                     if(mapCheck.get(i).get(j) instanceof VictoryBlock)//win condition
                     {
                        win = true;
                     }
                     else if(mapCheck.get(i).get(j) instanceof KeyBlock)//collect powerup
                     {
                        mapCheck.get(i).get(j).collect();
                        key = true;
                     }
                     else if(mapCheck.get(i).get(j) instanceof SpikeTrap)//death condition
                     {
                        if(key == false)
                        {
                           death = true;
                        }
                     }
                     return true;
                  }
               }
            }
         }
      }
      
      return false;
   }
   
   //accessors/mutators for various functions of a Player,
   public boolean won()
   {
      return win;
   }
   
   public boolean died()
   {
      return death;
   }
   public void newLife()
   {
      death = false;
   }
   public boolean hasKey()
   {
      return key;
   }
}