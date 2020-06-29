package mycontroller;

import controller.CarController;
import mycontroller.TraverseGoalStrategy.TraverseGoalStrategiesFactory;
import mycontroller.TraverseGoalStrategy.TraverseGoalStrategy;
import mycontroller.TraverseStrategies.ITraverseStrategy;
import mycontroller.TraverseStrategies.RightHandTraverse;
import mycontroller.TraverseStrategies.TraverseGuideStrategy.SimpleFuelConserveGuideAdapter;
import mycontroller.TraverseStrategies.TraverseGuideStrategy.SimpleHealthConserveGuideAdapter;
import swen30006.driving.Simulation;
import world.Car;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public class MyAutoController extends CarController {
    // How many minimum units the wall is away from the player.
    private TraverseGoalStrategy traverseGoalStrategy;

    // Car Speed to move at
    private final int CAR_MAX_SPEED = 1;

    public MyAutoController(Car car) {
        super(car);
        ITraverseStrategy traverseStrategy = null;

        if (Simulation.toConserve() == Simulation.StrategyMode.HEALTH) {
            traverseStrategy = new RightHandTraverse(new SimpleHealthConserveGuideAdapter());
        } else if (Simulation.toConserve() == Simulation.StrategyMode.FUEL) {
            traverseStrategy = new RightHandTraverse(new SimpleFuelConserveGuideAdapter());
        }

        traverseGoalStrategy = TraverseGoalStrategiesFactory
                .getInstance().ParcelsFinderStrategy(this, traverseStrategy);
    }

    @Override
    public void update() {

        // Gets what the car can see
        HashMap<Coordinate, MapTile> currentView = getView();

        if (getSpeed() < CAR_MAX_SPEED) {       // Need speed to turn and progress toward the exit
            applyForwardAcceleration();   // Tough luck if there's a wall in the way
        }

        if (traverseGoalStrategy.getGoal(currentView)) {
            traverseGoalStrategy.exit(currentView);
        }
    }
}
