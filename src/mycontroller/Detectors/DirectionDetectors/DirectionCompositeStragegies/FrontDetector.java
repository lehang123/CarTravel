package mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.HashMap;

public class FrontDetector extends OrientationDetector {

    public FrontDetector(IDirectionCheckerAdapter directionCheckerAdapter, WorldSpatial.Direction orientation) {
        this(directionCheckerAdapter, 1, orientation);
    }

    public FrontDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity, WorldSpatial.Direction orientation) {
        super(directionCheckerAdapter, sensitivity, orientation);
    }

    @Override
    public Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        return detectorTable.get(orientation).check(currentView, sensitivity);
    }

    @Override
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity, WorldSpatial.Direction orientation) {
        return detectorTable.get(orientation).checkAll(currentView, sensitivity);
    }
}
