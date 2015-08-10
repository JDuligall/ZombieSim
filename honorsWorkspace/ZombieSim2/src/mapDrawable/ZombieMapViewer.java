package mapDrawable;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;


/**
 * Created by Jacob on 29/07/2015.
 */
public class ZombieMapViewer extends JMapViewer {

    private final JSlider testSlider;
    private final JLabel text;
    private ArrayList<ZombieDrawable> zombies = new ArrayList<ZombieDrawable>();
    private double speed;


    public ZombieMapViewer(){

        this.speed = 0.000001;

        testSlider = new JSlider(0, 9);
        testSlider.setOrientation(JSlider.HORIZONTAL);
        testSlider.setBounds(100, 10, 150, 20);
        testSlider.setOpaque(false);
        testSlider.setToolTipText("Zombie Speed");
        testSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setSpeed(testSlider.getValue());
            }


        });
        testSlider.setFocusable(false);

        text = new JLabel();
        text.setText("Zombie Speed");
        text.setBounds(130, 15, 100, 50);



        add(text);
        add(testSlider);


    }

    private void setSpeed(int speed) {
        switch(speed) {
            case 1: this.speed = 0.00000001;
                break;
            case 2: this.speed = 0.0000002;
                break;
            case 3: this.speed = 0.0000004;
                break;
            case 4: this.speed = 0.0000006;
                break;
            case 5: this.speed = 0.0000010;
                break;
            case 6: this.speed = 0.0000014;
                break;
            case 7: this.speed = 0.0000018;
                break;
            case 8: this.speed = 0.0000022;
                break;
            case 9: this.speed = 0.0000026;
                break;
        }

    }

    public double getSpeed(){
        return this.speed;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(ZombieDrawable zomb: zombies){
//            if(this.)
//            if the zombie isn't on the screen, somehow don't paint it?
//            zomb.paint(g, this.getWidth(), this.getHeight());
            zomb.paint(g);
        }
//        g.dispose();
    }

    public void setZombies(ArrayList<ZombieDrawable> zombies){
        this.zombies = zombies;
    }


}
