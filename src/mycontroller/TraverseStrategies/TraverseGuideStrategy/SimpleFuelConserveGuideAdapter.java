package mycontroller.TraverseStrategies.TraverseGuideStrategy;

import mycontroller.Detectors.CheckerAdapters.WallBlockAdapter;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.FrontDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.LeftDetector;
import mycontroller.Detectors.DirectionDetectors.DirectionCompositeStragegies.RightDetector;
import mycontroller.algorithm.AlgorithmFactory;
import mycontroller.algorithm.DijkstraPQ;
import mycontroller.algorithm.SimpleFuelConservseWeight;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

public class SimpleFuelConserveGuideAdapter implements ITraverseGuideAdapter {
    private final int lavaDamage = 5; /*doesn't have the delta, estimate damage of lava is 5 per second*/
    private WallBlockAdapter lavaCheckAdapter = new WallBlockAdapter("lava");


    @Override
    public boolean isAheadSafe(WorldSpatial.Direction orientation,
                               HashMap<Coordinate, MapTile> currentView,
                               float currentHealth) {
        // since we only think fuel is important, as long as the trap doesn't kill you, it's safe

        FrontDetector frontDetector = new FrontDetector(lavaCheckAdapter, orientation);
        return (currentHealth > frontDetector.checkAll(currentView, 4).size() * lavaDamage) ||
                frontDetector.check(currentView) == null;
    }

    @Override
    public boolean isLeftSafe(WorldSpatial.Direction orientation,
                              HashMap<Coordinate, MapTile> currentView,
                              float currentHealth) {
        // since we only think fuel is important, as long as the trap doesn't kill you, it's safe
        LeftDetector leftDetector = new LeftDetector(lavaCheckAdapter, orientation);
        return (currentHealth > leftDetector.checkAll(currentView, 4).size() * lavaDamage) ||
                leftDetector.check(currentView) == null;
    }

    @Override
    public boolean isRightSafe(WorldSpatial.Direction orientation,
                               HashMap<Coordinate, MapTile> currentView,
                               float currentHealth) {
        // since we only think fuel is important, as long as the trap doesn't kill you, it's safe
        RightDetector rightDetector = new RightDetector(lavaCheckAdapter, orientation);
        return (currentHealth > rightDetector.checkAll(currentView, 4).size() * lavaDamage) ||
                rightDetector.check(currentView) == null;
    }


    // using some kind of algorithm to find the shortest/safest path to destination
    @Override
    public Coordinate MoveToDestination(HashMap<Coordinate, MapTile> currentView,
                                        Coordinate currentPosition,
                                        Coordinate parcelPosition,
                                        WorldSpatial.Direction orientation,
                                        float currentHealth) {

        Stack<Coordinate> pathStack = new Stack<>();
        /*if stack is empty, make one */
        while (!pathStack.empty()) {
            pathStack.pop();
        }

        DijkstraPQ<Coordinate> dijkstraPQ = AlgorithmFactory.getInstance().getDijkstraPQ();
        HashMap<Coordinate, HashMap<Coordinate, Integer>> adjList = dijkstraPQ.makeAdjList(currentView,
                currentPosition,
                orientation,
                new SimpleFuelConservseWeight(currentHealth));

        HashMap<Coordinate, Coordinate> parents = dijkstraPQ.run(adjList, currentPosition);
        Coordinate end = parcelPosition;
        pathStack.push(end);
        while (parents.get(end) != null) {
            end = parents.get(end);
            pathStack.push(end);
        }

        /*pop the beginning as it's the your current position*/
        if (pathStack.pop().equals(currentPosition)) {
            /*there is a way to destination point*/
            return pathStack.size() > 0 ? pathStack.pop() : null;
        } else {
            return null;
        }
    }
}
