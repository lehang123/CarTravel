package mycontroller.Detectors.CheckerAdapters;

import tiles.MapTile;
import utilities.Coordinate;

import java.util.HashMap;

/* An adapter that tells how to check*/
public interface IDirectionCheckerAdapter {

    /*check if the tile fit description*/
    boolean check(HashMap<Coordinate, MapTile> currentView, Coordinate coordinate);

    /*when check is fail, do no more further checking*/
    boolean failCheck();

    Coordinate getTileCoordinate();
}
