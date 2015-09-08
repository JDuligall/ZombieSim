package mapComponents;

import mapContents.Nd;
import mapContents.Way;
import mapDrawable.ZombieDrawable;

import java.util.ArrayList;

/**
 * Created by Jacob on 06/09/2015.
 */
public class House {


    private Way way;
    private ArrayList<Nd> nodes;

    public House(Way way, ArrayList<Nd> nodes){
        this.way = way;
        this.nodes = nodes;
    }


}
