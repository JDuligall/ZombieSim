package mapDrawable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jacob on 01/08/2015.
 */
public class DrawCanvas extends Canvas {
    private ArrayList<ZombieDrawable> zombies = new ArrayList<ZombieDrawable>();

    public DrawCanvas(ArrayList<ZombieDrawable> zombies){
        this.zombies = zombies;
    }


    @Override
    public void paint(Graphics g){
        System.out.println("DC paint");
        for(ZombieDrawable zomb: zombies){
            zomb.paint(g);
        }
        g.dispose();
    }
}
