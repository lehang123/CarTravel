package test;

public class Robot {
    int on_floor;
    boolean unwrapping;
    RobotsInfoListener robotsInfoListener;

    void unwrap(){
        if (robotsInfoListener.isSomeoneUnwrapping(on_floor)){
            // don't unwrap and go to the next floor that nobody is unwrapping
        }{
            unwrapping=true;
        }
    }

    void moveTo(int floor){

        if (robotsInfoListener.isSomeoneUnwrapping(floor)){
            // don't go there yet
        }else {
            on_floor = floor;
        }
    }



}
