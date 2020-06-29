package mycontroller.Detectors.DirectionDetectors.DirectionLeaf;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Detectors.DirectionDetectors.AbstractDirectionDetector;

public class SouthDetector extends AbstractDirectionDetector {

    public SouthDetector(IDirectionCheckerAdapter directionCheckerAdapter) {
        this(directionCheckerAdapter, 1);
    }

    public SouthDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity) {
        super(directionCheckerAdapter, sensitivity);
        this.y = -1;
    }
}
