package src_java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*
The Main Frame class is where game will be run from.  It will only contain one panel
and have a black background around this panel.
*/
public class PlatformerGame extends JFrame
{
   public PlatformerGame()
   {
      super("Project");
      
      Container contents = getContentPane();

      PlatformerPanel gp = new PlatformerPanel();
      
      contents.setLayout(new FlowLayout(1,20,20));//add gaps around panel
      
      contents.setBackground(Color.BLACK);
      
      contents.add(gp);
      
      setSize(850,850);
      setVisible(true);
      
      gp.requestFocus();
   
   }

   public static void main(String[] args)
   {
      PlatformerGame theFrame = new PlatformerGame();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
} 