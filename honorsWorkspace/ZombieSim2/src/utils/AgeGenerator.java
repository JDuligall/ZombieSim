package utils;

import java.util.Random;

/**
 * Created by Jacob on 03/09/2015.
 */
public class AgeGenerator {

    private Random rand;

    public AgeGenerator(){
        rand = new Random();
    }

    public int getAge(){
        int num = rand.nextInt(1001);
        int age = rand.nextInt(5);
        age++;

        if(num <= 65) {return age;}
        else if( 65 < num && num <= 131){return age+5;}
        else if( 131 < num && num <= 198){return age+10;}
        else if( 198 < num && num <= 267){return age+15;}
        else if( 267 < num && num <= 338){return age+20;}
        else if( 338 < num && num <= 406){return age+25;}
        else if( 406 < num && num <= 472){return age+30;}
        else if( 472 < num && num <= 534){return age+35;}
        else if( 534 < num && num <= 601){return age+40;}
        else if( 601 < num && num <= 671){return age+45;}
        else if( 671 < num && num <= 743){return age+50;}
        else if( 743 < num && num <= 809){return age+55;}
        else if( 809 < num && num <= 866){return age+60;}
        else if( 866 < num && num <= 910){return age+65;}
        else if( 910 < num && num <= 942){return age+70;}
        else if( 942 < num && num <= 966){return age+75;}
        else if( 966 < num && num <= 985){return age+80;}
        else if( 985 < num && num <= 1000){return age+85;}
        else{return -1;}


    }

}
