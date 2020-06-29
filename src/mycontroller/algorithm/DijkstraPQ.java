package mycontroller.algorithm;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

import java.util.*;

public class DijkstraPQ<T> {
    public HashMap<T, T> run(HashMap<T, HashMap<T, Integer>> adj, T src) {
        HashMap<T, Integer> dist = new HashMap<>();

        /* make a parent so it keeps the path of travel */
        HashMap<T, T> parents = new HashMap<>();
        /* src has no parent */
        parents.put(src, null);
        /* Initialize distances of all vertices as infinite.*/
        for (T t : adj.keySet())
            dist.put(t, Integer.MAX_VALUE);

        /* Create an empty priority_queue pq.  Every item
            of pq is a pair (weight, vertex). Weight (or
            distance) is used used as first item of pair
            as first item is by default used to compare
            two pairs */
        PriorityQueue<Node> pq = new PriorityQueue<>();

        /*Insert source vertex into pq and make its
          distance as 0.*/
        pq.add(new Node(src, 0));
        dist.put(src, 0);

        while (!pq.isEmpty()) {
            /*Extract minimum distance vertex from pq.
                Let the extracted vertex be u.*/
            T u = pq.remove().getNode();

            /*Loop through all adjacent of u and do
                following for every vertex v.*/
            HashMap<T, Integer> u_Neighbors = adj.get(u);
            if (u_Neighbors == null) {
                break;
            }
            for (T v : u_Neighbors.keySet()) {
                /* If there is a shorter path to v
                 through u. */
                if (dist.get(v) > (dist.get(u) + u_Neighbors.get(v))) {
                    /* Update distance of v */
                    dist.put(v, dist.get(u) + u_Neighbors.get(v));
                    /* Update parent of v as u*/
                    parents.put(v, u);
                    /* Insert v into the pq */
                    pq.add(new Node(v, dist.get(u) + u_Neighbors.get(v)));
                }
            }
        }
        return parents;
    }

    public HashMap<Coordinate, HashMap<Coordinate, Integer>> makeAdjList(HashMap<Coordinate, MapTile> currentView,
                                                                         Coordinate currentCoordinate,
                                                                         WorldSpatial.Direction orientation,
                                                                         PathWeightAdapter pathWeightAdapter) {
        HashMap<Coordinate, HashMap<Coordinate, Integer>> adjList = new HashMap<>();
        for (Coordinate coordinate : currentView.keySet()) {
            /*can't go through wall*/
            MapTile tile = currentView.get(coordinate);

            if (!pathWeightAdapter.filterOutPath(tile,
                    coordinate,
                    currentView,
                    orientation, currentCoordinate)) {
                continue;
            }
            adjList.put(coordinate, new HashMap<>());
        }

        for (Coordinate coordinate : adjList.keySet()) {
            for (Coordinate Ccoordinate : adjList.keySet()) {
                boolean areNeighbors = (Math.abs(coordinate.x - Ccoordinate.x) == 1
                        && coordinate.y == Ccoordinate.y) || (Math.abs(coordinate.y - Ccoordinate.y) == 1
                        && coordinate.x == Ccoordinate.x);
                if (areNeighbors) {
                    adjList.get(coordinate)
                            .put(Ccoordinate,
                                    pathWeightAdapter.setWeightForPath(currentView.get(Ccoordinate)));
                }
            }
        }
        return adjList;
    }

    class Node implements Comparable<Node> {

        public T getNode() {
            return node;
        }

        private T node;

        private int distance;

        Node(T node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }
}
