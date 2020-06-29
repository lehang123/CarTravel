package mycontroller.TraverseStrategies;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

public interface ITraverseStrategy {

    void exploring(CarController carController, HashMap<Coordinate, MapTile> currentView);

    boolean collectParcel(CarController carController,
                          HashMap<Coordinate, MapTile> currentView, Coordinate parcelPosition);

    boolean exit(CarController carController,
                 HashMap<Coordinate, MapTile> viewExplored, Coordinate exitPostion);
}
