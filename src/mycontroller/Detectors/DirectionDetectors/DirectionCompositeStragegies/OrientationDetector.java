package mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Detectors.DirectionDetectors.AbstractDirectionDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionLeaf.EastDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionLeaf.NorthDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionLeaf.SouthDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionLeaf.WestDetector;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

public abstract class OrientationDetector extends AbstractDirectionDetector {

    protected WorldSpatial.Direction orientation;
    protected Map<WorldSpatial.Direction, AbstractDirectionDetector> detectorTable;

    public OrientationDetector(IDirectionCheckerAdapter directionCheckerAdapter, WorldSpatial.Direction orientation) {
        this(directionCheckerAdapter, 1, orientation);

    }

    public OrientationDetector(IDirectionCheckerAdapter directionCheckerAdapter,
                               int sensitivity, WorldSpatial.Direction orientation) {
        super(directionCheckerAdapter, sensitivity);
        this.orientation = orientation;

        Map<WorldSpatial.Direction, AbstractDirectionDetector> detectorTable = new HashMap<>();
        detectorTable.put(WorldSpatial.Direction.EAST, new EastDetector(directionCheckerAdapter, sensitivity));
        detectorTable.put(WorldSpatial.Direction.WEST, new WestDetector(directionCheckerAdapter, sensitivity));
        detectorTable.put(WorldSpatial.Direction.NORTH, new NorthDetector(directionCheckerAdapter, sensitivity));
        detectorTable.put(WorldSpatial.Direction.SOUTH, new SouthDetector(directionCheckerAdapter, sensitivity));

        this.detectorTable = Collections.unmodifiableMap(detectorTable);
    }

    public abstract Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity,
                                     WorldSpatial.Direction orientation);

    public abstract ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity,
                                                   WorldSpatial.Direction orientation);

    @Override
    public Coordinate check(HashMap<Coordinate, MapTile> currentView) {
        return check(currentView, 1);
    }

    @Override
    public Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity) {
        return check(currentView, sensitivity, orientation);
    }

    @Override
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView) {
        return checkAll(currentView, 1);
    }

    @Override
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity) {
        return checkAll(currentView, sensitivity, orientation);
    }
}
