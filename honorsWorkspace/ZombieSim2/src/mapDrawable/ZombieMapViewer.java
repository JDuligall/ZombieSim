package mapDrawable;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;


/**
 * Created by Jacob on 29/07/2015.
 */
public class ZombieMapViewer extends JMapViewer {

    private ArrayList<ZombieDrawable> zombies = new ArrayList<ZombieDrawable>();
    private BufferedImage image = new BufferedImage(800, 800,
            BufferedImage.TYPE_INT_ARGB);

    DrawCanvas dc;

    public ZombieMapViewer(){
//        this.dc = new DrawCanvas(zombies);
//        dc.setSize(this.getSize());
//        //todo transparent?
//
//        add("Center",dc);
//        //todo why isn't the canvas showing up? is it behind the jframe painting stufF"?
    }

//    public void doPaint(){
//        System.out.println("woo");
//        dc.paint(dc.getGraphics());
//    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
//        System.out.println("PAINT COMPONENT DRAW CANVAS!!!");
        for(ZombieDrawable zomb: zombies){
//            if(this.)
//            if the zombie isn't on the screen, somehow don't paint it?
//            zomb.paint(g, this.getWidth(), this.getHeight());
            zomb.paint(g);
        }
        g.dispose();
    }
//
//    @Override
//    public void repaint(){
//        super.repaint();
//        Graphics g = image.getGraphics();
//
////        for(ZombieDrawable zomb: zombies){
////            zomb.paint(g);
////        }
//
//        g.setColor(Color.black);
//        g.drawOval(10,10,10,10);
//        g.dispose();
//
//    }


    public void setZombies(ArrayList<ZombieDrawable> zombies){
        this.zombies = zombies;
    }


}
