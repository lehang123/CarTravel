package mycontroller.TraverseStrategies.TraverseGuideStrategy;

import mycontroller.Detectors.CheckerAdapters.WallBlockAdapter;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.FrontDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.LeftDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.RightDetector;
import mycontroller.algorithm.AlgorithmFactory;
import mycontroller.algorithm.DijkstraPQ;
import mycontroller.algorithm.SimpleHealthConservseWeight;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

public class SimpleHealthConserveGuideAdapter implements ITraverseGuideAdapter {
    private WallBlockAdapter lavaCheckAdapter = new WallBlockAdapter("lava");

    @Override
    public boolean isAheadSafe(WorldSpatial.Direction orientation,
                               HashMap<Coordinate, MapTile> currentView,
                               float currentHealth) {
        return new FrontDetector(lavaCheckAdapter, orientation).check(currentView) == null;
    }

    @Override
    public boolean isLeftSafe(WorldSpatial.Direction orientation,
                              HashMap<Coordinate, MapTile> currentView,
                              float currentHealth) {

        return new LeftDetector(lavaCheckAdapter, orientation).check(currentView) == null;
    }

    @Override
    public boolean isRightSafe(WorldSpatial.Direction orientation,
                               HashMap<Coordinate, MapTile> currentView,
                               float currentHealth) {

        return new RightDetector(lavaCheckAdapter, orientation).check(currentView) == null;
    }


    // using some kind of algorithm to find the shortest/safest path to destination
    @Override
    public Coordinate MoveToDestination(HashMap<Coordinate, MapTile> currentView,
                                        Coordinate currentPosition, Coordinate parcelPosition,
                                        WorldSpatial.Direction orientation, float currentHealth) {
        Stack<Coordinate> pathStack = new Stack<>();
        Stack<Coordinate> pathStack2 = new Stack<>();

        /*if stack is empty, make one */
        int totalDamage = 0;
        while (!pathStack.empty()) {
            pathStack.pop();
        }
        DijkstraPQ<Coordinate> dijkstraPQ = AlgorithmFactory.getInstance().getDijkstraPQ();
        HashMap<Coordinate, HashMap<Coordinate, Integer>> adjList = dijkstraPQ.makeAdjList(currentView,
                currentPosition, orientation, new SimpleHealthConservseWeight(currentHealth));
        HashMap<Coordinate, Coordinate> parents = dijkstraPQ.run(adjList, currentPosition);
        Coordinate end = parcelPosition;
        pathStack.push(end);
        pathStack2.push(end);
        while (parents.get(end) != null) {
            end = parents.get(end);
            pathStack.push(end);
            pathStack2.push(end);
        }
        /*pop the beginning as it's the your current position*/
        pathStack.pop();
        pathStack2.pop();

        if (pathStack.size() > 0) {
            while (!pathStack2.empty()) {
                Coordinate c = pathStack2.pop();
                if (currentView.get(c).isType(MapTile.Type.TRAP)) {
                    TrapTile t = (TrapTile) currentView.get(c);
                    if (t.getTrap().equals("lava")) {
                        totalDamage += 5;
                    }
                }
            }

            // compute the total damage to the car if it is going to a destination(parcel point)
            // if the current health of the car is less than 2 times of the total damage
            // that means the car will will not have enough health to make its way back to the normal road
            totalDamage = totalDamage * 2;

            if (totalDamage >= currentHealth) {
                return null;
            } else {
                return pathStack.pop();
            }
        } else {
            return null;
        }
    }
}
