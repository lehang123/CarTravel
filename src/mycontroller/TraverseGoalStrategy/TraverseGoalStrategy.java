package mycontroller.TraverseGoalStrategy;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

public interface TraverseGoalStrategy {

    boolean getGoal(HashMap<Coordinate, MapTile> currentView);

    void exit(HashMap<Coordinate, MapTile> currentView);
}
