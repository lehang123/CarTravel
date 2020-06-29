package mycontroller.CarGPS;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

import java.util.HashMap;

public class ItemSensor {
    private SensorObserver sensorObserver;

    public ItemSensor(SensorObserver sensorObserver) {
        this.sensorObserver = sensorObserver;
    }

    public void check(HashMap<Coordinate, MapTile> currentView) {
        MapTile mapTile = null;

        for (Coordinate coordinate : currentView.keySet()) {
            mapTile = currentView.get(coordinate);
            if (mapTile != null) {
                if (!mapTile.isType(MapTile.Type.TRAP)) {
                    sensorObserver.TileFoundNotify(mapTile.getType(), coordinate);
                } else {
                    sensorObserver.TrapFoundNotify(((TrapTile) mapTile).getTrap(), coordinate);
                }
            }
        }
        sensorObserver.UpdateGPS(currentView);
    }
}
