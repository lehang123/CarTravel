package mycontroller.algorithm;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public interface PathWeightAdapter {

    /*set your own rule about the danger*/
    int setWeightForPath(MapTile tile);

    boolean filterOutPath(MapTile tile,
                          Coordinate coordinate,
                          HashMap<Coordinate, MapTile> currentView,
                          WorldSpatial.Direction orientation,
                          Coordinate currentCoordinate);
}
