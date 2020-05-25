
/**
 * Used for initiating the priority queue in order to compare pairs of a Tstation and estimated time of travel
 * when finding the shortest path in subwaySystem. 
 *
 * @author  Taylor Burke
 * @version 22 May 2020
 */

import java.util.Comparator;
import javafx.util.Pair; 

public class NodeDistComparator implements Comparator<Pair<TStation, Integer>>{
    public int compare(Pair<TStation, Integer> pair1, Pair<TStation, Integer> pair2) {
        int distDiff = pair1.getValue() - pair2.getValue();
        return distDiff > 0.0 ? 1:
               distDiff == 0.0 ? 0: -1;
    }
}

