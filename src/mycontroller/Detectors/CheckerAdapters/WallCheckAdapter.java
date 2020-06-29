package mycontroller.Detectors.CheckerAdapters;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

public class WallCheckAdapter implements IDirectionCheckerAdapter {
    private boolean doesFailCheck;
    private Coordinate matchCoordinate;

    @Override
    public boolean check(HashMap<Coordinate, MapTile> currentView, Coordinate coordinate) {
        /*reset fail check in the begin*/
        doesFailCheck = false;
        matchCoordinate = null;
        MapTile tile = currentView.get(coordinate);
        if (tile.getType().equals(MapTile.Type.WALL)) {
            matchCoordinate = coordinate;
            return true;
        } else if (coordinate.x < 0 || coordinate.y < 0) {// treat the outside world as wall as well
            matchCoordinate = coordinate;
            return true;
        }
        return false;
    }

    @Override
    public boolean failCheck() {
        return doesFailCheck;
    }

    @Override
    public Coordinate getTileCoordinate() {
        return matchCoordinate;
    }
}
