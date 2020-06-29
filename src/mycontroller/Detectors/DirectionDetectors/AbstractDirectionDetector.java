package mycontroller.Detectors.DirectionDetectors;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Utils;
import tiles.MapTile;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractDirectionDetector {

    protected IDirectionCheckerAdapter directionCheckerAdapter;
    protected int sensitivity;

    // to tell the direction East North West South
    protected int x;
    protected int y;

    public AbstractDirectionDetector(IDirectionCheckerAdapter directionCheckerAdapter) {
        this(directionCheckerAdapter, 1);
    }

    public AbstractDirectionDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity) {
        this.directionCheckerAdapter = directionCheckerAdapter;
        this.sensitivity = sensitivity;
    }

    public Coordinate check(HashMap<Coordinate, MapTile> currentView) {
        return check(currentView, sensitivity);
    }

    /* only check one target, find the closest one then return */
    public Coordinate check(HashMap<Coordinate, MapTile> currentView, int sensitivity) {
        Coordinate currentPosition = Utils.getCarCoordinate(currentView);
        for (int i = 1; i <= sensitivity; i++) {
            Coordinate coordinate = new Coordinate(currentPosition.x + (i * this.x),
                    currentPosition.y + (i * this.y));
            /* find match */
            if (directionCheckerAdapter.check(currentView, coordinate)) {
                coordinate = directionCheckerAdapter.getTileCoordinate();
                assert coordinate != null;
                return coordinate;
            } else {
                if (directionCheckerAdapter.failCheck()) {/*fail check, end checking*/
                    return null;
                }
            }
        }
        return null;
    }

    /*  check all targets if in that direction, find all of them then return */
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView) {
        return checkAll(currentView, sensitivity);
    }

    /*  check all targets if in that direction, find all of them then return */
    public ArrayList<Coordinate> checkAll(HashMap<Coordinate, MapTile> currentView, int sensitivity) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Coordinate currentPosition = Utils.getCarCoordinate(currentView);

        for (int i = 1; i <= sensitivity; i++) {
            Coordinate coordinate = new Coordinate(currentPosition.x + (i * this.x),
                    currentPosition.y + (i * this.y));
            /* find match */
            if (directionCheckerAdapter.check(currentView, coordinate)) {
                coordinate = directionCheckerAdapter.getTileCoordinate();
                assert coordinate != null;
                coordinates.add(coordinate);
            } else {
                if (directionCheckerAdapter.failCheck()) {
                    return coordinates;
                }
            }
        }
        return coordinates;
    }
}
