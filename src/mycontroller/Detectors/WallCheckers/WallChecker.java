package mycontroller.Detectors.WallCheckers;

import mycontroller.Detectors.CheckerAdapters.WallCheckAdapter;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.FrontDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.LeftDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.RightDetector;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public abstract class WallChecker {

    private WallCheckAdapter adapter = new WallCheckAdapter();

    public boolean isWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        return new FrontDetector(adapter, 1, orientation).check(currentView) != null;
    }

    public boolean isWallAtRight(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        return new RightDetector(adapter, 1, orientation).check(currentView) != null;
    }

    public boolean isWallAtLeft(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        return new LeftDetector(adapter, 1, orientation).check(currentView) != null;
    }

    public abstract boolean isFollowing(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView);
}
