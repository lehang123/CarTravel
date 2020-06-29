package mycontroller.TraverseStrategies.TraverseGuideStrategy;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

/*a interface to set rule about safety*/
public interface ITraverseGuideAdapter {
    boolean isAheadSafe(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView, float currentHealth);

    boolean isLeftSafe(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView, float currentHealth);

    boolean isRightSafe(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView, float currentHealth);

    /*gives the coordinate for the next move*/
    Coordinate MoveToDestination(HashMap<Coordinate, MapTile> currentView,
                                 Coordinate currentPosition,
                                 Coordinate destinationPoint,
                                 WorldSpatial.Direction orientation,
                                 float currentHealth);
}
