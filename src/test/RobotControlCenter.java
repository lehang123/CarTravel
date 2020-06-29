package test;

import java.util.ArrayList;

public class RobotControlCenter implements RobotsInfoListener{

    private ArrayList<Robot> robots;

    public RobotControlCenter(ArrayList<Robot> robots) {
        this.robots = robots;
        for (Robot robot : robots) {
            robot.robotsInfoListener = this;
        }
    }


    @Override
    public Boolean isSomeoneUnwrapping(int on_floor) {

        for (Robot robot : robots) {
            if (robot.on_floor == on_floor && robot.unwrapping){
                return true;
            }
        }
        return false;
    }
}
