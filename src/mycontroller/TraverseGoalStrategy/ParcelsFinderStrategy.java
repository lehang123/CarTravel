package mycontroller.TraverseGoalStrategy;

import controller.CarController;
import mycontroller.CarGPS.GPSRecorder;
import mycontroller.CarGPS.ItemSensor;
import mycontroller.TraverseStrategies.ITraverseStrategy;
import mycontroller.Utils;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ParcelsFinderStrategy implements TraverseGoalStrategy {

    private ItemSensor itemSensor;
    private CarController car;
    private ITraverseStrategy traverseStrategy;
    private GPSRecorder gpsRecorder;

    public ParcelsFinderStrategy(CarController car,
                                 ITraverseStrategy traverseStrategy) {
        gpsRecorder = new GPSRecorder();
        this.itemSensor = new ItemSensor(gpsRecorder);
        this.car = car;
        this.traverseStrategy = traverseStrategy;
    }

    @Override
    public boolean getGoal(HashMap<Coordinate, MapTile> currentView) {

        if (car.numParcelsFound() >= car.numParcels()) {
            return true;
        }
        Set<Coordinate> parcels = gpsRecorder.getParcels();
        itemSensor.check(currentView);
        if (parcels.size() != 0) {
            ArrayList<Coordinate> plist = new ArrayList<>();
            plist.addAll(parcels);
            Coordinate nearestParcel = Utils.getNearest(plist, currentView, false);
            if (traverseStrategy.collectParcel(car, gpsRecorder.getGPSView(), nearestParcel)) {
                parcels.remove(nearestParcel);
            }
        } else {
            exploring(currentView);
        }
        return false;
    }

    private void exploring(HashMap<Coordinate, MapTile> currentView) {
        traverseStrategy.exploring(car, currentView);
    }

    @Override
    public void exit(HashMap<Coordinate, MapTile> currentView) {
        itemSensor.check(currentView);
        Coordinate exitPoint = gpsRecorder.getExitPoint();
        if (exitPoint == null || traverseStrategy.exit(car, gpsRecorder.getGPSView(), exitPoint)) {
            traverseStrategy.exploring(car, currentView);
        }
    }
}
