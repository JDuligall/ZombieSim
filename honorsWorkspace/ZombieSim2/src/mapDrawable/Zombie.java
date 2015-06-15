package mapDrawable;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.PathIterator;
import java.util.List;
import java.util.Map;

import mapContents.Node;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;

public class Zombie extends MapPolygonImpl implements Runnable {
	private Boolean first;
	private double x;
	private double y;
	private double xInc;
	private double yInc;
	private double lat;
	private double lon;
	private Path2D path;
	private PathIterator pathIt;
	private Node startNode;
	private Node prevNode;

	private JMapViewer map;
	protected TileSource tileSource;

	public Zombie(JMapViewer map, Node startNode) {


		this.lat = startNode.getLat();
		this.lon = startNode.getLon();
		this.map = map;
		//first = true;
		//xInc = 0.0000003;
		//yInc = 0.0000003;
		this.startNode = startNode;
		prevNode = null;
		
		Point p = map.getMapPosition(lat, lon);
		if (p != null) {
		 this.x = p.getX();
		 this.y = p.getY();
		}
		
		//x = map.(startNode.getLat());
	}

	@Override
	public void paint(Graphics g, List<Point> points) {

		Graphics2D g2d = (Graphics2D) g.create();
//		this.path = buildPath(points);

//		if (first) {
//			this.pathIt =path.getPathIterator(null);
//			this.x = points.get(0).getX();
//			this.y = points.get(0).getY();
//			first = false;
//		}

		// g2d.fill(new Ellipse2D.Double(points.get(0).getX(),
		// points.get(0).getY(), 10, 10));
		// g2d.fill(new Ellipse2D.Double(this.x, this.y,10,10));
		g2d.fill(new Ellipse2D.Double(x, y, 10, 10));
		System.out.println(x+"   "+y);
		g2d.dispose();
	}

	private Path2D buildPath(List<Point> points) {
		Path2D path = new Path2D.Double();
		if (points != null && points.size() > 0) {
			Point firstPoint = points.get(0);
			path.moveTo(firstPoint.getX(), firstPoint.getY());
			for (Point p : points) {
				path.lineTo(p.getX(), p.getY());
			}
		}

		return path;
	}

	// Executes when start is called
	@Override
	public void run() {
//		if (pathIt != null) {
//			if (!pathIt.isDone()) {
//				double[] coordinates = new double[6];
//				switch (pathIt.currentSegment(coordinates)) {
//					case PathIterator.SEG_MOVETO:
//					case PathIterator.SEG_LINETO: {
//						this.x = coordinates[0];
//						this.y = coordinates[1];
//						break;
//					}
//				}
//				
//				//map.get
//				// lat = lat - xInc;
//				// lon = lon - yInc;
//				Point p = map.getMapPosition(lat, lon);
//				if (p != null) {
//				 x = p.getX();
//				 y = p.getY();
//				}
//				pathIt.next();
//			}else{
//				this.pathIt =path.getPathIterator(null);
//			}
//		}
	}

}