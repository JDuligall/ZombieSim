package main;

import java.awt.Color;
import java.awt.Stroke;
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

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class MapViewer {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		JMapViewer map = new JMapViewer();
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
		Osm param = null;
		Way myStreet = null;
		HashMap<BigInteger, Coordinate> locations = new HashMap<BigInteger, Coordinate>();

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
		 MapPolyLine polygon = new MapPolyLine(coords);
		 polygon.setColor(Color.red);
		 polygon.setBackColor(Color.red);
		 map.addMapPolygon(polygon);
		 
		 //map.add
		

		// store all the nodes in a list so i can get the coordinates using the
		// reference. ------------DONE its called locations

		// TODO Store all nd of my street as coordinates
		// make a polygon from the coordinates.
		// add the polygon.

		frame.add(map);
		frame.setVisible(true);
		if(myStreet!=null)System.out.println("allGood");
		
		// while(true){
		// coord.setLat(coord.getLat()+0.0000001);
		// map.repaint();
		// }

	}

}
