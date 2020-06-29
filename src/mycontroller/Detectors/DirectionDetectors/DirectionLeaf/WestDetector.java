package mycontroller.Detectors.DirectionDetectors.DirectionLeaf;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Detectors.DirectionDetectors.AbstractDirectionDetector;

public class WestDetector extends AbstractDirectionDetector {

    public WestDetector(IDirectionCheckerAdapter directionCheckerAdapter) {
        this(directionCheckerAdapter, 1);
    }

    public WestDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity) {
        super(directionCheckerAdapter, sensitivity);
        this.x = -1;
    }
}
