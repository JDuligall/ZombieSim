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
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mapContents.Node;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import utils.LineIterator;

public class ZombieDrawable implements Runnable {

	private double x;
	private double y;
	private Node curNode;
	private float lat;
	private float lon;
	private ZombieMapViewer map;
	private Node prevNode;
	private boolean first;
	private Node toNode;
	private boolean hasIterated;
	private boolean newIter;
	private LineIterator iter;
	private double speed;


	public ZombieDrawable(Node node, ZombieMapViewer map) {
		this.curNode = node;
		this.prevNode = null;
		this.lat = node.getLat();
		this.lon = node.getLon();
		this.map = map;
		this.hasIterated = true;
		this.newIter = true;
		this.speed = 0.000001;

	}

	public void paint(Graphics gra) {
//		int h = map.getHeight();
//		int w = map.getWidth();

		if(x != -1 && y != -1 ) {
			gra.setColor(Color.DARK_GRAY);
			gra.fillOval((int) this.x - 5, (int) this.y - 5, 10, 10);
		}
	}
	
	public void paint(Graphics gra, int width, int height) {
//		if(this.x > 0 && this.x < width && this.y > 0 && this.y < height) {
			//Graphics2D g2d = (Graphics2D) g.create();
			// g2d.fill(new Ellipse2D.Double(position.getX()-5, position.getY()-5,
			// 10, 10));
			gra.setColor(Color.DARK_GRAY);
			//gra.fill(new Ellipse2D.Double(this.x - 5, this.y - 5, 10, 10));
			gra.fillOval((int) this.x - 5, (int) this.y - 5, 10, 10);
//			gra.fillOval(10,10,10,10);
//		}
	}

	@Override
	public void run() {		
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

}
