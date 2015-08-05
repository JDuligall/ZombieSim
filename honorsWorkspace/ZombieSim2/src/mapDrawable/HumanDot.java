package mapDrawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mapContents.Node;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import utils.LineIterator;

public class HumanDot extends MapMarkerDot implements Runnable {

	private double x;
	private double y;
	private Node curNode;
	private float lat;
	private float lon;
	private JMapViewer map;
	private Node prevNode;
	private boolean first;
	private Node toNode;
	private boolean hasIterated;
	private boolean newIter;
	private LineIterator iter;

	public HumanDot(Coordinate coord, Node node, JMapViewer map) {
		super(coord);

		this.curNode = node;
		this.prevNode = null;
		this.lat = node.getLat();
		this.lon = node.getLon();  
		this.map = map;
		this.hasIterated = true;
		this.newIter = true;
	}

	@Override
	public void paint(Graphics g, Point position, int radio) {
		if(this.isVisible()){
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(new Ellipse2D.Double(this.x - 5, this.y - 5, 10, 10));
			g2d.dispose();
			//g2d.getClipBounds().intersects(r);
		}else{
			this.setVisible(false);
		}
		
	}

	@Override
	public void run() {		
		// first put zombie in curNodes pos
		setLoc(this.lat, this.lon);

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
				iter = new LineIterator(line);
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

	}

	// iterate along line to neighbour
	// mark neighbour as the one that has just been visited and move to new
	// neighbour UNLESS only original neighbour, then go back.

	public void setLoc(float lat, float lon) {
		Point p = map.getMapPosition(lat, lon);
		if (p != null) {
			this.x = p.getX();
			this.y = p.getY();
		}
	}

}
