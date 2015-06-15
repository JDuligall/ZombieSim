package mapDrawable;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

    public class TestLine extends MapPolygonImpl {
        private Line2D l;

		public TestLine(List<? extends ICoordinate> points) {
            super(null, null, points);
        }

        @Override
        public void paint(Graphics g, List<Point> points) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getColor());
            g2d.setStroke(new BasicStroke(10));
            Line2D line = buildLine(points);
            l=line;
            g2d.draw(line);
            g2d.dispose();
        }

        private Line2D buildLine(List<Point> points) {
            Line2D line = new Line2D.Double();
            //if (points != null && points.size() > 0) {
                line.setLine(points.get(0), points.get(1));
            //} 
            
            return line;
        }
        
        public Line2D getLine(){
        	return l;
        }
        
    }

