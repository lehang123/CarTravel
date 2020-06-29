package mycontroller.algorithm;

import utilities.Coordinate;

public class AlgorithmFactory {

    private static volatile AlgorithmFactory instance = null;
    private DijkstraPQ<Coordinate> dijkstraPQ;

    private AlgorithmFactory() {
        dijkstraPQ = new DijkstraPQ<>();
    }

    public static AlgorithmFactory getInstance() {
        if (instance == null) {
            synchronized (AlgorithmFactory.class) {
                if (instance == null) {
                    instance = new AlgorithmFactory();
                }
            }
        }
        return instance;
    }

    public DijkstraPQ<Coordinate> getDijkstraPQ() {
        return dijkstraPQ;
    }
}
