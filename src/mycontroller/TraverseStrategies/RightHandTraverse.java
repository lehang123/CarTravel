package mycontroller.TraverseStrategies;

import controller.CarController;
import mycontroller.Detectors.WallCheckers.RightHandWallChecker;
import mycontroller.Detectors.WallCheckers.WallChecker;
import mycontroller.TraverseStrategies.TraverseGuideStrategy.ITraverseGuideAdapter;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.HashMap;

public class RightHandTraverse implements ITraverseStrategy {

    private WallChecker wallChecker;
    private ITraverseGuideAdapter traverseSafetyGuide;
    private boolean isFollowingWall;
    private Coordinate enterPoint;
    private boolean isGoingRound;

    public RightHandTraverse(ITraverseGuideAdapter traverseSafetyGuide) {
        this.wallChecker = new RightHandWallChecker();
        this.traverseSafetyGuide = traverseSafetyGuide;
    }

    @Override
    public void exploring(CarController carController, HashMap<Coordinate, MapTile> currentView) {
        // keep in mind that turning call can't be reverse
        Coordinate currentPosition = new Coordinate(carController.getPosition());
        WorldSpatial.Direction orientation = carController.getOrientation();
        // when it is following wall
        if (wallChecker.isFollowing(orientation, currentView)) {
            if (!isFollowingWall) {// wasn't following wall
                isFollowingWall = true;
                enterPoint = new Coordinate(carController.getPosition());
            } else if (currentPosition.equals(enterPoint)) {
                //time to find a new world
                isGoingRound = true;
            }
        } else {
            // was following wall
            if (isFollowingWall) {
                // turn right keep following the wall
                if (traverseSafetyGuide.isRightSafe(orientation, currentView, carController.getHealth())) {
                    if (!isGoingRound) {
                        carController.turnRight();
                    } else {
                        isFollowingWall = false;
                        isGoingRound = false;
                    }
                } else {
                    isFollowingWall = false;
                    // leave wall, clear point
                }
            }
        }

        if (wallChecker.isWallAhead(orientation, currentView)
                || !traverseSafetyGuide.isAheadSafe(orientation, currentView, carController.getHealth())) {
            if (!wallChecker.isWallAtLeft(orientation, currentView)
                    && traverseSafetyGuide.isLeftSafe(orientation, currentView, carController.getHealth())) {
                carController.turnLeft();

            } else if (!wallChecker.isWallAtRight(orientation, currentView)
                    && traverseSafetyGuide.isRightSafe(orientation, currentView, carController.getHealth())) {
                carController.turnRight();
            }
        }
    }

    @Override
    public boolean collectParcel(CarController carController,
                                 HashMap<Coordinate, MapTile> currentView,
                                 Coordinate parcelPosition) {
        return moveToDestination(carController, currentView, parcelPosition);
    }

    @Override
    public boolean exit(CarController carController, HashMap<Coordinate, MapTile> viewExplored, Coordinate exitPostion) {
        return moveToDestination(carController, viewExplored, exitPostion);
    }

    private boolean moveToDestination(CarController carController,
                                      HashMap<Coordinate, MapTile> view, Coordinate destination) {
        WorldSpatial.Direction orientation = carController.getOrientation();
        Coordinate currentPosition = new Coordinate(carController.getPosition());
        Coordinate nextCoordinate = traverseSafetyGuide.MoveToDestination(view,
                currentPosition, destination, orientation, carController.getHealth());
        if (nextCoordinate != null) {
            int x_different = nextCoordinate.x - currentPosition.x;
            int y_different = nextCoordinate.y - currentPosition.y;
            switch (orientation) {
                case EAST:
                    if (y_different == 1) {
                        carController.turnLeft();
                    } else if (y_different == -1) {
                        carController.turnRight();
                    }
                    break;

                case NORTH:
                    if (x_different == 1) {
                        carController.turnRight();
                    } else if (x_different == -1) {
                        carController.turnLeft();
                    }
                    break;

                case SOUTH:
                    if (x_different == 1) {
                        carController.turnLeft();
                    } else if (x_different == -1) {
                        carController.turnRight();
                    }
                    break;

                case WEST:
                    if (y_different == 1) {
                        carController.turnRight();
                    } else if (y_different == -1) {
                        carController.turnLeft();
                    }
                    break;

                default:
                    break;
            }

        } else {// probably a dead end, keep traversing
            view = carController.getView();
            exploring(carController, view);
        }
        return currentPosition.equals(destination) || nextCoordinate == null;
    }
}
