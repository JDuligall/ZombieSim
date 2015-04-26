package main;

import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

public class Main {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        JMapViewer map = new JMapViewer();
        OfflineOsmTileSource src = new OfflineOsmTileSource("file:///C:/Users/Jacob/Documents/Victoria%20Fourth%20Year/jmapviewer/assets/Tiles", 16, 19);
        //map.setTileSource(src);
        frame.add(map);
        frame.setVisible(true);

    }

}
