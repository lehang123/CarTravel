package mycontroller.algorithm;

import mycontroller.Utils;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class SimpleHealthConservseWeight implements PathWeightAdapter {
    private static final float LavaDamage = 5;
    private float currentHealth;

    public SimpleHealthConservseWeight(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public int setWeightForPath(MapTile tile) {

        if (tile.isType(MapTile.Type.TRAP)) {
            String trapType = ((TrapTile) tile).getTrap();
            switch (trapType) {
                case "lava":
                    return 99;
                case "health":
                    return 5;
                case "water":
                    return 1;
                case "parcel":
                    return 1;
                default:
                    return 6;
            }
        } else {
            return 6;
        }
    }

    @Override
    public boolean filterOutPath(MapTile tile,
                                 Coordinate coordinate,
                                 HashMap<Coordinate, MapTile> currentView,
                                 WorldSpatial.Direction orientation,
                                 Coordinate currentCoordinate) {
        if (tile.isType(MapTile.Type.WALL) ||
                (coordinate.x < 0 || coordinate.y < 0)
                || !Utils.hasNeighbors(currentView, coordinate)
                || coordinate.equals(Utils.carBackCoordinate(currentView, currentCoordinate, orientation))) {
            return false;
        }
        return true;
    }
}
