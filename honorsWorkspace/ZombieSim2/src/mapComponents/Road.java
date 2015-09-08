package mapComponents;

import mapContents.Nd;
import mapContents.Way;
import mapDrawable.ZombieDrawable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jacob on 06/09/2015.
 */
public class Road {

    private ArrayList<ZombieDrawable> zombies;
    private Way way;
    private ArrayList<Nd> nodes;

    public Road(Way way, ArrayList<Nd> nodes){
        this.way = way;
        this.nodes = nodes;
        this.zombies = new ArrayList<ZombieDrawable>();
    }


    public void addZombie(ZombieDrawable zomb) {
        this.zombies.add(zomb);
    }

    public void removeZombie(ZombieDrawable zomb){
        this.zombies.remove(zomb);
    }

    public void doCollision(ZombieDrawable zomb){
        //TODO go through all zombies in the list and check to see if the current zombie collides.
        //TODO if they do collide then infect the new one.
//        double x1 = zomb.getX();
//        double y1 = zomb.getY();
        Rectangle rect1 = zomb.getBounds();

        for(ZombieDrawable zom : zombies){
            if(!zom.isDiseased()){
                Rectangle rect2 = zom.getBounds();
                if(rect1.intersects(rect2)){
                    zom.setDiseased(true);
                }
            }
//            double x2 = zom.getX();
//            double y2 = zom.getY();

//            if((x1 + 5 <= x2+5 && x2-5 <= x1 + 5)||(x1 - 5 <= x2+5 && x2-5 <= x1 - 5)){
//                if((y1 + 5 <= y2+5 && y2-5 <= y1 + 5)||(y1 - 5 <= y2+5 && y2-5 <= y1 - 5)){
//                    zom.setDiseased(true);
//                }
//            }

        }
    }
}
