package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static Coordinate getCarCoordinate(HashMap<Coordinate, MapTile> currentView) {
        int highest = -1;
        Coordinate right_top_coordinate = null;
        for (Coordinate coordinate : currentView.keySet()) {
            if (coordinate.x + coordinate.y > highest) {
                highest = coordinate.x + coordinate.y;
                right_top_coordinate = coordinate;
            }
        }
        assert right_top_coordinate != null;
        return new Coordinate(right_top_coordinate.x - 4, right_top_coordinate.y - 4);
    }

    public static Coordinate getNearest(ArrayList<Coordinate> coordinates,
                                        HashMap<Coordinate, MapTile> currentView,
                                        boolean rmCoordinate) {

        Coordinate carPosition = Utils.getCarCoordinate(currentView);
        Coordinate nearestCoordinate = null;
        int minDistance = 9999999;

        for (Coordinate coordinate : coordinates) {
            int manhattan = Math.abs(coordinate.x - carPosition.x) + Math.abs(coordinate.y - carPosition.y);
            if (minDistance > manhattan) {
                minDistance = manhattan;
                nearestCoordinate = coordinate;
            }
        }

        if (rmCoordinate) {
            coordinates.remove(nearestCoordinate);
        }

        return nearestCoordinate;
    }

    public static Coordinate carBackCoordinate(HashMap<Coordinate, MapTile> currentView,
                                               Coordinate currentPosition,
                                               WorldSpatial.Direction orientation) {
        switch (orientation) {
            case EAST:
                return new Coordinate(currentPosition.x - 1, currentPosition.y);
            case NORTH:
                return new Coordinate(currentPosition.x, currentPosition.y - 1);
            case SOUTH:
                return new Coordinate(currentPosition.x, currentPosition.y + 1);
            case WEST:
                return new Coordinate(currentPosition.x + 1, currentPosition.y);
            default:
                break;
        }
        return null;
    }

    public static boolean hasNeighbors(HashMap<Coordinate, MapTile> currentView, Coordinate coordinate) {
        int x = coordinate.x;
        int y = coordinate.y;

        Coordinate coordinate1 = new Coordinate(x + 1, y);
        Coordinate coordinate2 = new Coordinate(x - 1, y);
        Coordinate coordinate3 = new Coordinate(x, y + 1);
        Coordinate coordinate4 = new Coordinate(x, y - 1);

        MapTile mapTile1 = currentView.get(new Coordinate(x + 1, y));
        MapTile mapTile2 = currentView.get(new Coordinate(x - 1, y));
        MapTile mapTile3 = currentView.get(new Coordinate(x, y + 1));
        MapTile mapTile4 = currentView.get(new Coordinate(x, y - 1));

        boolean check1 = currentView.containsKey(coordinate1) && !mapTile1.isType(MapTile.Type.WALL);
        boolean check2 = currentView.containsKey(coordinate2) && !mapTile2.isType(MapTile.Type.WALL);
        boolean check3 = currentView.containsKey(coordinate3) && !mapTile3.isType(MapTile.Type.WALL);
        boolean check4 = currentView.containsKey(coordinate4) && !mapTile4.isType(MapTile.Type.WALL);

        return check1 || check2 || check3 || check4;
    }
}
