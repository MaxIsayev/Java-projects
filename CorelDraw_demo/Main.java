/*
*PROGRAM PAINTING FIGURE WITH LINES

Copyright by Max Isayev 
*/  

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

/// main class 
public class Main {
	 
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
         public void run() {
           createAndShowGUI();
         }
       });
   }

   private static void createAndShowGUI() {
 
       JFrame f = new JFrame("Corel Draw Demo");
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.add(new MyApplet());
       f.pack();
       f.setVisible(true);
   }
}
