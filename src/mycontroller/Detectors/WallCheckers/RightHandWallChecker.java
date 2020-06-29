package mycontroller.Detectors.WallCheckers;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class RightHandWallChecker extends WallChecker {

    @Override
    public boolean isFollowing(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
        return isWallAtRight(orientation, currentView);
    }
}
