package mycontroller.TraverseGoalStrategy;

import controller.CarController;
import mycontroller.TraverseStrategies.ITraverseStrategy;

public class TraverseGoalStrategiesFactory {

    // lazy Singleton, thread safe
    private static volatile TraverseGoalStrategiesFactory instance = null;

    private TraverseGoalStrategiesFactory() {
    }

    public static TraverseGoalStrategiesFactory getInstance() {
        if (instance == null) {
            synchronized (TraverseGoalStrategiesFactory.class) {
                if (instance == null) {
                    instance = new TraverseGoalStrategiesFactory();
                }
            }
        }
        return instance;
    }

    public ParcelsFinderStrategy ParcelsFinderStrategy(CarController car,
                                                       ITraverseStrategy traverseStrategy) {
        return new ParcelsFinderStrategy(car, traverseStrategy);
    }
}
