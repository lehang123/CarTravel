package mycontroller.Detectors.DirectionDetectors.DirectionLeaf;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Detectors.DirectionDetectors.AbstractDirectionDetector;

public class NorthDetector extends AbstractDirectionDetector {

    public NorthDetector(IDirectionCheckerAdapter directionCheckerAdapter) {
        this(directionCheckerAdapter, 1);
    }

    public NorthDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity) {
        super(directionCheckerAdapter, sensitivity);
        this.y = 1;
    }
}
