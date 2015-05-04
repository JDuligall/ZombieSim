package mapDrawable;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;


	 public class Zombie extends MapPolygonImpl implements Runnable {
		 private Boolean first;
		private double x;
		private double y;
		 
	        public Zombie(List<? extends ICoordinate> points) {
	            super(null, null, points);
	            first = true;
	        }

	        @Override
	        public void paint(Graphics g, List<Point> points) {
	            Graphics2D g2d = (Graphics2D) g.create();
	            if(first){
	            	this.x = points.get(0).getX();
	            	this.y = points.get(0).getY();
	            	first = false;
	            }
	            g2d.fill(new Ellipse2D.Double(this.x, this.y,10,10));
	            g2d.dispose();
	        }

	        //Executes when start is called
			@Override
			public void run() {
				this.x = this.x-1;
				this.y = this.y-1;
				
			}
	    }