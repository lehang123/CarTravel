package mycontroller.algorithm;

import mycontroller.Utils;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class SimpleFuelConservseWeight implements PathWeightAdapter {

    private static final float LavaDamage = 5;
    private float currentHealth;

    public SimpleFuelConservseWeight(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public int setWeightForPath(MapTile tile) {

        if (tile.isType(MapTile.Type.TRAP)) {
            String trapType = ((TrapTile) tile).getTrap();
            switch (trapType) {
                case "lava":
                    if (currentHealth < (LavaDamage * 4)) {
                        return 100;
                    } else {
                        return 3;
                    }
                case "health":
                    if (currentHealth < (LavaDamage * 4)) {
                        return 1;
                    } else {
                        return 3;
                    }
                case "water":
                    if (currentHealth < (LavaDamage * 4)) {
                        return 1;
                    } else {
                        return 3;
                    }
                case "parcel":
                    return 3;
                default:
                    return 3;
            }
        } else {
            return 3;
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

        if (tile.isType(MapTile.Type.TRAP)) {
            String trapType = ((TrapTile) tile).getTrap();
            switch (trapType) {
                case "lava":
                    if (currentHealth < (LavaDamage * 2)) {
                        return false;
                    }
                    break;
                default:
                    return true;
            }
        }
        return true;
    }
}
