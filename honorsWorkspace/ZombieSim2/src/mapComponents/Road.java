package mapComponents;

import mapContents.Nd;
import mapContents.Tag;
import mapContents.Way;
import mapDrawable.ZombieDrawable;

import java.awt.*;
import java.util.*;

/**
 * Created by Jacob on 06/09/2015.
 */
public class Road {

    private ArrayList<ZombieDrawable> zombies;
    private Way way;
    private ArrayList<Nd> nodes;
    public String roadName;
    private Random rand;

    public Road(Way way, ArrayList<Nd> nodes){
        this.way = way;
        this.nodes = nodes;
        this.rand = new Random();

        roadName = getRoadName(way);

        this.zombies = new ArrayList<ZombieDrawable>();
    }

    private String getRoadName(Way way) {
        String name = "";

        java.util.List<Object> wayStuff= way.getRest();
        for(Object obj: wayStuff) {
            if (obj.getClass().equals(Tag.class)) {
                Tag t = (Tag) obj;
                if (t.getK().equals("name")) {
                    name = t.getV();
                }
            }
        }
        return name;
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
            if(!zom.hasDisease()){
                Rectangle rect2 = zom.getBounds();
                if(rect1.intersects(rect2)){
                    int i = rand.nextInt(100);
//                    zom.setDiseased(true);
                    if(i <= zom.map.getDiseaseChance()) {
                        zom.startDiseaseTimer();
                        System.out.println("INFECTED");
                    }
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
