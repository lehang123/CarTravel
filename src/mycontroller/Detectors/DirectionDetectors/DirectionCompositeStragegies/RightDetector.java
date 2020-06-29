package mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.HashMap;

public class RightDetector extends OrientationDetector {

    public RightDetector(IDirectionCheckerAdapter directionCheckerAdapter, WorldSpatial.Direction orientation) {
        this(directionCheckerAdapter, 1, orientation);
    }

    public RightDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity, WorldSpatial.Direction orientation) {
        super(directionCheckerAdapter, sensitivity, orientation);
    }

    @Override
    public Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        WorldSpatial.Direction checkDirection = WorldSpatial.changeDirection(orientation,
                WorldSpatial.RelativeDirection.RIGHT);
        return detectorTable.get(checkDirection).check(currentView, sensitivity);
    }

    @Override
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        WorldSpatial.Direction checkDirection = WorldSpatial.changeDirection(orientation,
                WorldSpatial.RelativeDirection.RIGHT);
        return detectorTable.get(checkDirection).checkAll(currentView, sensitivity);
    }
}
