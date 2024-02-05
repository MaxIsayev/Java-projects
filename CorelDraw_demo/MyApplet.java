
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;



//class defining applet
class MyApplet extends JApplet {
			
       private int[] xPoints = new int [100];
       private int[] yPoints = new int [100];
       private int nPoints = 0;     
       
       
       public MyApplet() { 
         addMouseListener(
            new MouseAdapter() {
               public void mousePressed(MouseEvent e) {
                  UpdatePolygon(e.getX(),e.getY());
               }
            });
       }

       private void updatePolygon(int x, int y) {

          if ((xPoints[nPoints]!=x) || (yPoints[nPoints]!=y)) {

             repaint(0, 0, 1000, 1000);
             xPoints[nPoints]=x;
             yPoints[nPoints]=y;
             repaint(0, 0, 1600, 1200);
             nPoints++;

          }
       }     

       public void paint(Graphics g) {
            

            super.paint(g);
            g.setColor(Color.YELLOW);
            
            g.fillPolygon(xPoints,yPoints,nPoints);
            
            g.setColor(Color.BLACK);

            g.drawPolygon(xPoints,yPoints,nPoints);
            
       }
}
