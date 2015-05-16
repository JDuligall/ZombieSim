package mapDrawable;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.List;
import java.util.Map;

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
	
	JMapViewer map;
	
	protected TileSource tileSource;

	public Zombie(List<? extends ICoordinate> points, JMapViewer map) {
		super(null, null, points);

		lat = points.get(0).getLat();
		lon = points.get(0).getLon();
		this.map = map;
		first = true;
		xInc = 0.0000003;
		yInc = 0.0000003;
	}

	@Override
	public void paint(Graphics g, List<Point> points) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		if(first){
		  this.x = points.get(0).getX();
		  this.y = points.get(0).getY();
		  first = false;
		 }
		//g2d.fill(new Ellipse2D.Double(points.get(0).getX(), points.get(0).getY(), 10, 10));
		// g2d.fill(new Ellipse2D.Double(this.x, this.y,10,10));
		g2d.fill(new Ellipse2D.Double(x,y,10,10));
		g2d.dispose();
	}

	// Executes when start is called
	@Override
	public void run() {
		lat = lat-xInc;
		lon = lon-yInc;
		Point p = map.getMapPosition(lat, lon);
		if(p!=null){
			x = p.getX();
			y = p.getY();
		}
	}
	

	
	
}