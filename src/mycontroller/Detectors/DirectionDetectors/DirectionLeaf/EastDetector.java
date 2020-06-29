package mycontroller.Detectors.DirectionDetectors.DirectionLeaf;

import mycontroller.Detectors.CheckerAdapters.IDirectionCheckerAdapter;
import mycontroller.Detectors.DirectionDetectors.AbstractDirectionDetector;

public class EastDetector extends AbstractDirectionDetector {

    public EastDetector(IDirectionCheckerAdapter directionCheckerAdapter) {
        this(directionCheckerAdapter, 1);
    }

    public EastDetector(IDirectionCheckerAdapter directionCheckerAdapter, int sensitivity) {
        super(directionCheckerAdapter, sensitivity);
        this.x = 1;
    }
}
