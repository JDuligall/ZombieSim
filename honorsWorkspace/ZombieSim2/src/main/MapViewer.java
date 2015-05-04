package main;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mapContents.Nd;
import mapContents.Node;
import mapContents.Osm;
import mapContents.Way;
import mapContents.Tag;
import mapDrawable.MapPolyLine;
import mapDrawable.Zombie;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.MapRectangleImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

public class MapViewer {
	
	private List<Thread> threads = new ArrayList<Thread>();
	private JMapViewer map;
	private JFrame frame;
	private Osm param;
	private Way myStreet;
	private HashMap<BigInteger, Coordinate> locations;
	
	public MapViewer(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		map = new JMapViewer();
		// OfflineOsmTileSource src = new
		// OfflineOsmTileSource("file:///C:/Users/Jacob/Documents/Victoria%20Fourth%20Year/jmapviewer/assets/Tiles",
		// 16, 19);
		// map.setTileSource(src);

		// <node id="2338412630" visible="true" version="2" changeset="20096780"
		// timestamp="2014-01-20T02:52:07Z" user="howdystranger" uid="1839337"
		// lat="-41.2995998" lon="174.7791143"/>
		// <node id="2629352691" visible="true" version="1" changeset="20096780"
		// timestamp="2014-01-20T02:52:05Z" user="howdystranger" uid="1839337"
		// lat="-41.2994774" lon="174.7790564"/>
		// <node id="2629352686" visible="true" version="1" changeset="20096780"
		// timestamp="2014-01-20T02:52:05Z" user="howdystranger" uid="1839337"
		// lat="-41.2994808" lon="174.7789696"/>
		// <node id="3301422781" visible="true" version="1" changeset="28294949"
		// timestamp="2015-01-20T21:40:08Z" user="howdystranger" uid="1839337"
		// lat="-41.2992232" lon="174.7792977"/>

		// Coordinate coord = new Coordinate(-41.2995998, 174.7791143);
		// Coordinate coord1 = new Coordinate(-41.2994774, 174.7790564);
		// Coordinate coord2 = new Coordinate(-41.2994808, 174.7789696);
		// Coordinate coord3 = new Coordinate(-41.2992232, 174.7792977);
		//
		// MapMarkerDot marker = new MapMarkerDot(coord);
		//
		// map.addMapMarker(marker);
		//
		// map.setDisplayPosition(coord, 19);
		//
		//
		// MapPolygonImpl polygon = new
		// MapPolygonImpl(coord,coord1,coord2,coord3);
		// polygon.setColor(Color.red);
		// polygon.setBackColor(Color.red);
		//
		//
		// map.addMapPolygon(polygon);
		param = null;
		myStreet = null;
		locations = new HashMap<BigInteger, Coordinate>();

		readXML();

		List<Object> contents = param.getBoundOrUserOrPreferences();
		// read through the OSM file to get the contents
		for (int i = 0; i < contents.size(); i++) {
			Object cur = contents.get(i);
			
			if (cur.getClass().equals((mapContents.Node.class))) {
				Node curNode = (Node)cur;
				locations.put(curNode.getId(), new Coordinate(curNode.getLat(),curNode.getLon()));
				
				
			} else if (cur.getClass().equals((mapContents.Way.class))) {
				Way way = (Way) contents.get(i);
				List<Object> wayNodes = way.getRest();
				for (int j = 0; j < wayNodes.size(); j++) {
					if (wayNodes.get(j).getClass().equals(Tag.class)) {
						Tag t = (Tag) wayNodes.get(j);
						if (t.getV().equals("Buckle Street")) {
							myStreet = way;
							break;
						}
					}
				}

			}
		}
		
		// myStreet.getRest()
		
		List<Object> myStreetTags = myStreet.getRest();
		ArrayList<Nd> nds = new ArrayList<Nd>();
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		
		for(int i = 0;i<myStreetTags.size();i++){
			Object cur = myStreetTags.get(i);
			if(cur.getClass().equals(mapContents.Nd.class)){
				Nd curNd = (Nd)cur;
				nds.add(curNd);

				coords.add(locations.get(curNd.getRef()));
			}
		}
		
		 //MapPolygonImpl polygon = new MapPolygonImpl(coords);
		 MapPolyLine polygon1 = new MapPolyLine(coords);
		 //<bounds minlat="-41.2994300" minlon="174.7788100" maxlat="-41.2989000" maxlon="174.7796000"/>
		 MapRectangleImpl rect = new MapRectangleImpl(new Coordinate(-41.2984300,174.7768100), new Coordinate(-41.2999000,174.7806000));
		 map.addMapRectangle(rect);
		 //ArrayList<Coordinate> lol = new ArrayList<Coordinate>();
		 //Coordinate coord3 = new Coordinate(-41.2992232, 174.7792977);
		 //lol.add(coord3);
		 
		 
		 //Makes the zombie and thread for zombie
		 Zombie polygon = new Zombie(coords);
		 Thread thread = new Thread(polygon);
		 this.threads.add(thread);//Adds thread to loop set
		 
		 
		 polygon1.setColor(Color.red);
		 polygon1.setBackColor(Color.red);
		 //map.add
		 map.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(map.getZoom() > 15){
					map.addMapPolygon(polygon);
				 	map.addMapPolygon(polygon1);
				 }else{
					 map.removeMapPolygon(polygon);
					 map.removeMapPolygon(polygon1);
				 }
				
			}
		});

		// store all the nodes in a list so i can get the coordinates using the
		// reference. ------------DONE its called locations

		// TODO Store all nd of my street as coordinates
		// make a polygon from the coordinates.
		// add the polygon.

		frame.add(map);
		frame.setVisible(true);
		if(myStreet!=null)System.out.println("allGood");
		
		while(true){
			step();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void readXML() {
		try {
			JAXBContext context = JAXBContext.newInstance(Osm.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			param = (Osm) unMarshaller.unmarshal(new FileInputStream(
					"../../mapFiles/myHouse.osm"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {

		new MapViewer();
		

	}
	
	
	public void step(){
		for(Thread t : threads){
			t.run();
			map.repaint();
		}
	}

}
