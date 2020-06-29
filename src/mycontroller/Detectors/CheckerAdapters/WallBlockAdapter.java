package mycontroller.Detectors.CheckerAdapters;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

import java.util.HashMap;

/* the checker that doesn't see through wall */
public class WallBlockAdapter implements IDirectionCheckerAdapter {

    private MapTile.Type type;

    private String trapType;
    private boolean doesFailCheck;
    private Coordinate matchCoordinate;

    public WallBlockAdapter(MapTile.Type type) {
        this.type = type;
    }

    public WallBlockAdapter(String trapType) {
        this.trapType = trapType;
        this.type = MapTile.Type.TRAP;
    }

    @Override
    public boolean check(HashMap<Coordinate, MapTile> currentView, Coordinate coordinate) {
        /*reset fail check in the begin*/
        doesFailCheck = false;
        matchCoordinate = null;
        MapTile tile = currentView.get(coordinate);
        if (tile.getType().equals(MapTile.Type.WALL) && !type.equals(MapTile.Type.WALL)) {
            doesFailCheck = true;
            return false;
        }

        if (tile.isType(type)) {
            if (tile.isType(MapTile.Type.TRAP)) {
                if ((tile instanceof TrapTile) && ((TrapTile) tile).getTrap().equals(trapType)) {
                    matchCoordinate = coordinate;
                    return true;
                }
            } else {
                matchCoordinate = coordinate;
                return true;
            }
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
