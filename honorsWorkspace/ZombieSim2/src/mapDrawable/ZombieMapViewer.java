package mapDrawable;

import mapContents.Node;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Created by Jacob on 29/07/2015.
 */
public class ZombieMapViewer extends JMapViewer{

    private final JSlider ZomSpeedSlider;
    private final JSlider diseaseWaitSlider;
    private final JButton infect;
    private final JLabel ZomSpeedtext;
    private final JLabel diseaseWaitText;
    private final JSlider diseaseChanceSlider;
    private final JLabel diseaseChanceText;
    private ArrayList<ZombieDrawable> zombies = new ArrayList<ZombieDrawable>();
    private double speed;
    private Node myHouse;
    private ZombieDrawable selected = null;
    private long diseaseTime;
    private int diseaseChance;

    public ZombieMapViewer(){
//        this.speed = 0.000001;
        this.speed = 0.0000002;
        this.diseaseTime = 10000;
        this.diseaseChance = 80;

        infect = new JButton("Infect");
        infect.setBounds(30, 180, 70,20);
        infect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    selected.setDiseased(true);
                }
            }
        });

        ZomSpeedSlider = new JSlider(0, 9, 1);
        ZomSpeedSlider.setOrientation(JSlider.HORIZONTAL);
        ZomSpeedSlider.setBounds(100, 10, 150, 20);
        ZomSpeedSlider.setOpaque(false);
        ZomSpeedSlider.setToolTipText("Zombie Speed");
        ZomSpeedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setSpeed(ZomSpeedSlider.getValue());
            }
        });
        ZomSpeedSlider.setFocusable(false);

        ZomSpeedtext = new JLabel();
        ZomSpeedtext.setText("Zombie Speed");
        ZomSpeedtext.setBounds(130, 15, 100, 50);


        diseaseWaitSlider = new JSlider(1, 9, 1);
        diseaseWaitSlider.setOrientation(JSlider.HORIZONTAL);
        diseaseWaitSlider.setBounds(300, 10, 150, 20);
        diseaseWaitSlider.setOpaque(false);
        diseaseWaitSlider.setToolTipText("Infection Time");
        diseaseWaitSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setDiseaseWait(diseaseWaitSlider.getValue());
            }
        });
        diseaseWaitSlider.setFocusable(false);

        diseaseWaitText = new JLabel();
        diseaseWaitText.setText("Infection Time");
        diseaseWaitText.setBounds(330, 15, 100, 50);

        diseaseChanceSlider = new JSlider(1, 11, 9);
        diseaseChanceSlider.setOrientation(JSlider.HORIZONTAL);
        diseaseChanceSlider.setBounds(450, 10, 150, 20);
        diseaseChanceSlider.setOpaque(false);
        diseaseChanceSlider.setToolTipText("Chance of Disease");
        diseaseChanceSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setDiseaseChance(diseaseChanceSlider.getValue());
            }
        });
        diseaseWaitSlider.setFocusable(false);

        diseaseChanceText = new JLabel();
        diseaseChanceText.setText("Chance of Disease");
        diseaseChanceText.setBounds(480, 15, 150, 50);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 1) {
                    for (ZombieDrawable zomb : zombies) {
                        if (zomb.checkClick(e.getX(), e.getY())) {
                            zomb.setClicked(true);
                            if (selected != null) {
                                selected.setClicked(false);
                            }
                            selected = zomb;
                            return;
                        }
                    }
                }
            }
        });


        add(ZomSpeedtext);
        add(ZomSpeedSlider);
        add(diseaseWaitSlider);
        add(diseaseWaitText);
        add(diseaseChanceText);
        add(diseaseChanceSlider);
        add(infect);


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

    private void setDiseaseWait(int wait) {
        switch(wait) {
            case 1: this.diseaseTime = 0;
                break;
            case 2: this.diseaseTime = 10000;
                break;
            case 3: this.diseaseTime = 20000;
                break;
            case 4: this.diseaseTime = 30000;
                break;
            case 5: this.diseaseTime = 40000;
                break;
            case 6: this.diseaseTime = 50000;
                break;
            case 7: this.diseaseTime = 60000;
                break;
            case 8: this.diseaseTime = 120000;
                break;
            case 9: this.diseaseTime = 240000;
                break;
        }
        System.out.println(this.diseaseTime);
    }

    private void setDiseaseChance(int chance) {
        switch(chance) {
            case 1: this.diseaseChance = 0;
                break;
            case 2: this.diseaseChance = 10;
                break;
            case 3: this.diseaseChance = 20;
                break;
            case 4: this.diseaseChance = 30;
                break;
            case 5: this.diseaseChance = 40;
                break;
            case 6: this.diseaseChance = 50;
                break;
            case 7: this.diseaseChance = 60;
                break;
            case 8: this.diseaseChance = 70;
                break;
            case 9: this.diseaseChance = 80;
                break;
            case 10: this.diseaseChance = 90;
                break;
            case 11: this.diseaseChance = 100;
                break;
        }
        System.out.println(this.diseaseChance);
    }

    public double getSpeed(){
        return this.speed;
    }

    public void setMyHouse(Node myHouse){
        this.myHouse = myHouse;
    }

    public Node getMyHouse(){
        return this.myHouse;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(ZombieDrawable zomb: zombies){
//            if(this.)
//            if the zombie isn't on the screen, somehow don't paint it?
//            zomb.paint(g, this.getWidth(), this.getHeight());
            zomb.paint(g);

        }
        if(selected!=null){
            g.setColor(Color.white);
            g.fillRect(10, 200, 100, 150);

            g.setColor(Color.black);
            g.drawString(selected.getFirstName() + "" , 20, 220);
            g.drawString(""+selected.getLastName(), 20, 235);
            g.drawString(""+selected.getAge(), 20, 255);
            g.drawString("Gender: "+selected.getGender(), 20, 275);
            g.drawString("Lat:", 20, 295);
            g.drawString(""+selected.lat,20,310);
            g.drawString("Lon:",20,325);
            g.drawString(""+selected.lon,20,340);

//            g.drawString(selected.getCurrentRoad().toString(), 20, 390);

        }
//        g.dispose();
    }

    public void setZombies(ArrayList<ZombieDrawable> zombies){
        this.zombies = zombies;
    }


    public long getDiseaseTime() {
        return diseaseTime;
    }

    public int getDiseaseChance() {
        return diseaseChance;
    }
}
