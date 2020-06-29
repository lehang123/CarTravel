package mycontroller.CarGPS;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GPSRecorder implements SensorObserver {

    private Coordinate exitPoint;
    private Set<Coordinate> parcels = new HashSet<>();
    private HashMap<Coordinate, MapTile> GPSView = new HashMap<>();

    public Set<Coordinate> getParcels() {
        return parcels;
    }

    public Coordinate getExitPoint() {
        return exitPoint;
    }

    public HashMap<Coordinate, MapTile> getGPSView() {
        return GPSView;
    }

    @Override
    public void TileFoundNotify(MapTile.Type type, Coordinate coordinate) {
        if (type.equals(MapTile.Type.FINISH)) {
            /*found finish point, store it first*/
            exitPoint = coordinate;
        }
    }

    @Override
    public void TrapFoundNotify(String trapType, Coordinate coordinate) {
        if (trapType.equals("parcel")) {
            parcels.add(coordinate);
        }
    }

    @Override
    public void UpdateGPS(HashMap<Coordinate, MapTile> currentView) {
        GPSView.putAll(currentView);
    }
}
