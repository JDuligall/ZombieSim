package mapDrawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.*;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.util.shapes.GHPoint3D;
import main.MapViewer;
import mapComponents.Road;
import mapContents.Node;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import utils.LineIterator;

public class ZombieDrawable  implements Runnable {

	private final GraphHopper hopper;
	private double x;
	private double y;
	private Node curNode;
	public float lat;
	public float lon;
	public ZombieMapViewer map;
	private Node prevNode;
	private Node toNode;
	private boolean hasIterated;
	private boolean newIter;
	private LineIterator iter;
	private ArrayList<Coordinate> pathCoords;
	private boolean pathRequested;
	private Road currentRoad;

	private String firstName;
	private String lastName;
	private String gender;
	private int age;

	private boolean diseased;

	private Coordinate toCoord;
	private Coordinate curCoord;
	private boolean clicked;
	private boolean diseaseStarted;


	public ZombieDrawable(Node node, ZombieMapViewer map, GraphHopper hopper) {
		this.hopper = hopper;
		this.curNode = node;
		this.prevNode = null;
		this.lat = node.getLat();
		this.lon = node.getLon();
		this.map = map;
		this.hasIterated = true;
		this.newIter = true;
		this.pathRequested = false;
		this.diseased = false;
		this.diseaseStarted = false;
		currentRoad = node.getRoad();
		currentRoad.addZombie(this);

	}

	private void requestPath() {
		double latFrom = curNode.getLat();
		double lonFrom = curNode.getLon();
		double latTo = map.getMyHouse().getLat();
		double lonTo = map.getMyHouse().getLon();
		GHRequest req = new GHRequest(latFrom, lonFrom, latTo, lonTo).
				setWeighting("fastest").
				setVehicle("car").
				setLocale(Locale.ENGLISH);
		GHResponse rsp = hopper.route(req);
		if(rsp.hasErrors()){
//			System.out.println("errors");
		}
		pathCoords = new ArrayList<Coordinate>();
		for(GHPoint3D p : rsp.getPoints()){
			pathCoords.add(new Coordinate(p.getLat(),p.getLon()));
		}
		//remove the first node as it is the cur node
		curCoord = 	pathCoords.remove(0);

	}

	public void paint(Graphics gra) {
//		int h = map.getHeight();
//		int w = map.getWidth();

		if(x != -1 && y != -1 ) {
			if (clicked) {
				gra.setColor(Color.red);
				gra.fillOval((int) this.x - 6, (int) this.y - 6, 12, 12);
			}
//			gra.setColor(Color.green);
//			gra.fillOval();
			if(diseased){
				gra.setColor(Color.green);
			}
			else{
				gra.setColor(Color.DARK_GRAY);
			}
			gra.fillOval((int) this.x - 5, (int) this.y - 5, 10, 10);

//			gra.setColor(Color.blue);
//			if(getCurrentRoad()!=null && getCurrentRoad().roadName!=null){
//				gra.drawString(getCurrentRoad().roadName, (int) this.x + 10, (int) this.y + 10);
//			}
//			gra.drawRect((int) this.getBounds().getX(), (int) this.getBounds().getY(), 10, 10);
		}
	}

	@Override
	public void run() {

		if(!pathRequested) {
			pathRequested = true;
			requestPath();
		}
		if(diseased) {
			currentRoad.doCollision(this);
		}
		randStep();
//		goHomeStep();
	}

	private void goHomeStep() {
		// first put zombie in curNodes pos
		setLoc(this.lat, this.lon);

		// choose a neighbour from the path
		if (hasIterated) {
			toCoord = pathCoords.remove(0);
			hasIterated = false;

		} else {

			// draw a line to the neighbour node
			if (newIter) {
				Line2D line = new Line2D.Double(curCoord.getLat(),
						curCoord.getLon(), toCoord.getLat(), toCoord.getLon());
				iter = new LineIterator(line,map.getSpeed());
				newIter = false;
			} else {
				if (iter.hasNext()) {
					Point2D nextPoint = iter.next();
					lat = (float) nextPoint.getX();
					lon = (float) nextPoint.getY();
					// System.out.println(lat + "     " + lon);
				} else {
					newIter = true;
					hasIterated = true;
					curCoord = toCoord;
				}

			}
		}
	}

	private void randStep(){
		// first put zombie in curNodes pos
		//map.removeMapMarker(this);
		setLoc(this.lat, this.lon);
//		System.out.println(lat + "      " + lon);
		// randomly choose a neighbour
		if (hasIterated) {
			ArrayList<Node> curNeighbours = curNode.getNeighbours();
			Random rand = new Random();
			if (curNeighbours.size() == 1) {
				Node n = curNeighbours.get(0);
				this.toNode = n;
				prevNode = curNode;
				hasIterated = false;

			} else {
				boolean done = false;
				while (!done) {
					if (curNeighbours.size() > 0) {
						int index = rand.nextInt(curNeighbours.size());
						Node n = curNeighbours.get(index);

						if (n != prevNode) {
							this.toNode = n;
							prevNode = curNode;
							done = true;
							hasIterated = false;

						}
					}else{
						//if a node has no neighbours... wtf
						return;
					}
				}
			}
		} else {

			// draw a line to the neighbour node
			if (newIter) {
				Line2D line = new Line2D.Double(curNode.getLat(),
						curNode.getLon(), toNode.getLat(), toNode.getLon());
				iter = new LineIterator(line,map.getSpeed());
				newIter = false;
			} else {
				if (iter.hasNext()) {
					Point2D nextPoint = iter.next();
					lat = (float) nextPoint.getX();
					lon = (float) nextPoint.getY();
					// System.out.println(lat + "     " + lon);
				} else {
					newIter = true;
					hasIterated = true;

					currentRoad.removeZombie(this);
					currentRoad = curNode.getRoad();
					currentRoad.addZombie(this);

					curNode = toNode;
				}

				// List<Point2D> ary = new ArrayList<Point2D>();
				// Line2D line = new
				// Line2D.Double(curNode.getLat(),curNode.getLon(),
				// toNode.getLat(), toNode.getLon());
				// Point2D current;
				// for(Iterator<Point2D> iter = new LineIterator(line,0.00001);
				// iter.hasNext();) {
				// current =iter.next();
				// ary.add(current);
				// }
				// System.out.println(ary.size());

			}

		}
		//map.addMapMarker(this);
	}

	// iterate along line to neighbour
	// mark neighbour as the one that has just been visited and move to new
	// neighbour UNLESS only original neighbour, then go back.

	public void startDiseaseTimer(){
		diseaseStarted = true;

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setDiseased(true);
			}
		},map.getDiseaseTime());
	}

	private void setLoc(float lat, float lon) {
		Point p = map.getMapPosition(lat, lon);
		if (p != null) {
			this.x = p.getX();
			this.y = p.getY();
		}else {
			this.x = -1;
			this.y = -1;
		}
	}

	public boolean checkClick(double clickX, double clickY){
		return (this.x-5 <= clickX && clickX <= this.x+5 && this.y-5 <= clickY && clickY <= this.y+5);
	}

	public boolean isClicked(){return this.clicked;}

	public void setClicked(boolean clicked) {this.clicked = clicked;}

	public String getFirstName() {return firstName;}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isDiseased() {return diseased;}

	public boolean hasDisease(){return diseaseStarted;}

	public void setDiseased(boolean diseased) {this.diseased = diseased;}

	public Road getCurrentRoad() {return currentRoad;}

	public void setCurrentRoad(Road currentRoad) {this.currentRoad = currentRoad;}

	public double getX(){return this.x;}

	public double getY(){return this.y;}

	public Rectangle getBounds(){return new Rectangle((int)this.x-5, (int)this.y-5, 10,10);}
}