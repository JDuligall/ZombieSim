package utils;

import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jacob on 02/09/2015.
 */
public class NameGenerator {
    private ArrayList<String> girlFirst = new ArrayList<String>();
    private ArrayList<String> boyFirst = new ArrayList<String>();
    private ArrayList<String> last = new ArrayList<String>();

    private Random randomGen = new Random();

    public NameGenerator(){
        parseFirstNames();
        parseLastNames();

    }

    public String getGirlName(){
        int index = randomGen.nextInt(girlFirst.size());
        return girlFirst.get(index);

    }

    public String getBoyName(){
        int index = randomGen.nextInt(boyFirst.size());
        return boyFirst.get(index);
    }

    public String getSurname(){
        int index = randomGen.nextInt(last.size());
        return last.get(index);
    }

    private void parseFirstNames() {

        try {
            FileReader input = new FileReader("assets/firstNames.txt");

            BufferedReader bufRead = new BufferedReader(input);
            String line = null;



            while ( (line = bufRead.readLine()) != null)
            {
                String[] splitLine = line.split(",");
                if(splitLine[1].equals("F")){
                    if(girlFirst.size() < 1000) {
                        girlFirst.add(splitLine[0]);
                    }
                }else{
                    if(boyFirst.size() < 1000) {
                        boyFirst.add(splitLine[0]);
                    }
                }

            }
            bufRead.close();
            input.close();
            System.out.println(girlFirst.size());
            System.out.println(boyFirst.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseLastNames() {
        try {
            FileReader input = new FileReader("assets/lastNames.txt");

            BufferedReader bufRead = new BufferedReader(input);
            String line = null;

            int count = 0;
            while ( (line = bufRead.readLine()) != null && count < 1000)
            {
                String[] splitLine = line.split("\t");
                last.add(WordUtils.capitalizeFully(splitLine[0]));
                count++;

            }
            bufRead.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
