package mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.HashMap;

public class LeftDetector extends OrientationDetector {

    public LeftDetector(IDirectionCheckerAdapter directionCheckerAdapter, WorldSpatial.Direction orientation) {
        this(directionCheckerAdapter, 1, orientation);
    }

    public LeftDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity, WorldSpatial.Direction orientation) {
        super(directionCheckerAdapter, sensitivity, orientation);
    }

    @Override
    public Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        WorldSpatial.Direction checkDirection = WorldSpatial.changeDirection(orientation,
                WorldSpatial.RelativeDirection.LEFT);
        return detectorTable.get(checkDirection).check(currentView, sensitivity);
    }

    @Override
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        WorldSpatial.Direction checkDirection = WorldSpatial.changeDirection(orientation,
                WorldSpatial.RelativeDirection.LEFT);
        return detectorTable.get(checkDirection).checkAll(currentView, sensitivity);
    }
}
