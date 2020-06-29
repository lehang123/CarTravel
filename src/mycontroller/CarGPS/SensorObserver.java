package mycontroller.CarGPS;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

public interface SensorObserver {
    void TileFoundNotify(MapTile.Type type, Coordinate coordinate);

    void TrapFoundNotify(String trapType, Coordinate coordinate);

    void UpdateGPS(HashMap<Coordinate, MapTile> currentView);
}
