
/**
 * The subway system is a a way to navigate through Boston.
 * The subway systems mains representation is a bidirectional weighted adjacency 
 * list graph of t stations
 *
 * @author Taylor Burke
 * @version 20 May 2019
 */

import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Hashtable;
import javafx.util.Pair; 
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class subwaySystem {
    // instance variables
    private WeightedAdjustListGraph<TStation> representation;

    /**
     * Constructor for objects of class subway_system. It calls the file which holds all the subway information
     * and constructs the bidirectional adjacency list graph 
     * 
     * @param fileName      the file containing all of the information of nodes to construct the graph from
     * 
     */
    public subwaySystem(String fileName){
        WeightedAdjustListGraph<TStation> representation = new WeightedAdjustListGraph<TStation>();
        //int counter = 0;
        try{
            Scanner scan = new Scanner(new FileReader(fileName)); 
            
            while (scan.hasNextLine()){
                //counter++;
                //String[] info = sc.nextLine().split(
                //System.out.println(scan.nextLine()); 
                String[] info = scan.nextLine().split(",");
                String origin_stop = info[0].trim();
                String origin_color = info[1].trim();
                String destination_stop = info[2].trim();
                String destination_color = info[3].trim();
                String type_of_travel = info[4].trim();
                int travel_time = Integer.valueOf(info[5].trim());
                
                TStation station1 = new TStation(origin_stop,origin_color);
                TStation station2 = new TStation(destination_stop,
                                                destination_color);
                
                representation.addVertex(station1);
                representation.addVertex(station2);
                
                representation.addEdge(station1, station2, type_of_travel, travel_time);
            }
        } catch (FileNotFoundException exp){
            System.out.println(exp);
        }
        //System.out.println(counter);// should be 127
        this.representation = representation;
    } 
    
    
    /**
     * recursive helper method to find all the possible permutations of all the stations 
     * that need to visited. (representing order of travel)
     * 
     * @param stationsToVisit       all the stations that need to be visited
     *        newPermutation        permutations on the current ordering (stack specific)
     */
    private static ArrayList<List<TStation>> pathPermutations(List<TStation> stationsToVisit){
        if (stationsToVisit.size() == 1) { 
            
            ArrayList<List<TStation>> lastStationArray = new ArrayList<List<TStation>>();
            lastStationArray.add(stationsToVisit);
            
            return lastStationArray;
        } else {
            int numStations = stationsToVisit.size();
            ArrayList<List<TStation>> permutations = pathPermutations(stationsToVisit.subList(0, numStations-1));
            ArrayList<List<TStation>> newPermutations = new ArrayList<List<TStation>>();
            
            
            for (List<TStation> permutation : permutations){
                for (int i = 0; i < numStations; i++){
                    List<TStation> newPermutation = new ArrayList<TStation>();
                    
                    newPermutation.addAll(permutation.subList(0,i));
                    newPermutation.add(stationsToVisit.get(numStations-1));
                    newPermutation.addAll(permutation.subList(i,permutation.size()));
                    
                    newPermutations.add(newPermutation);
                }
            }
            return newPermutations;
        }
    }
    
    /**
     * commutively calculates and returns the sum of the transit time for a path 
     * 
     * @param path          the path to be taken through the subway system
     *        pathLenghs    a mapping between every TStation to another station for transit time
     */
    private int findPathLength(Vector<TStation> path, Hashtable<TStation,  Hashtable<TStation, Integer>> pathLengths){
        int pathLength = 0;
        
        for (int i = 0; i < path.size()-1; i++){
             TStation start = path.get(i);
             TStation end = path.get(i+1);
             pathLength += pathLengths.get(start).get(end);
        }
        
        return pathLength;
    }
    
    /**
     * Finds all the minimum distances between a node and every other node in the graph (subway system). Uses a priority queue as
     * is typical of dijksta's algorithm
     *
     * @param   start                       The node from which to branch out from and find all the path lenghs
     * @return distances, predecessors      Distances contains all the distances from the start node to every other node in the graph 
     *                                      predecessors contains a mapping between the path from a node to another node (can be used to 
     *                                      trace the path from the start node to the end node
     */
    private Pair<Hashtable<TStation, Integer>, Hashtable<TStation, TStation>> dijkstra (TStation start){
        Hashtable<TStation, Integer> distances = new Hashtable<TStation, Integer>();
        HashSet<TStation> visited = new HashSet<TStation>();
        PriorityQueue<Pair<TStation, Integer>> pq; 
        pq =  new PriorityQueue<Pair<TStation, Integer>>(this.representation.getNumVertices(), new NodeDistComparator());
        Hashtable<TStation, TStation> predecessors = new Hashtable<TStation, TStation>(); 
       

        for (TStation node : this.representation.getVertices()){ 
            if (node.equals(start)){  distances.put(start, 0); 
            } else {distances.put(node, Integer.MAX_VALUE);} 
            pq.offer(new Pair<TStation, Integer> (node, distances.get(node)));
        }
        
        int distance;
        Pair<TStation, Integer> nextNode;

        while(!pq.isEmpty()){ //while the queue is not empty
            
            nextNode = pq.remove(); //remove the node closest to the start node
            if (visited.contains(nextNode)){ continue;} //don't go to nodes that are already visited
                
            distance = nextNode.getValue(); 
            
            LinkedList<Edge<TStation>> neighbors = this.representation.getSuccessors(nextNode.getKey());
            for (Edge<TStation> neighbor : neighbors){
                TStation nextStation = neighbor.getStop2();
                int newDistance = distance + neighbor.getTime();
                
                if(newDistance < distance){newDistance = distance;}//integer overflow catch
                
                if (newDistance < distances.get(nextStation)){
                    
                    if (predecessors.contains(nextStation)) {predecessors.replace(nextStation, neighbor.getStop1());
                    } else {predecessors.put(nextStation, neighbor.getStop1());}
                    
                    distances.replace(nextStation,newDistance);
                    pq.offer(new Pair<TStation,Integer>(nextStation, newDistance));
                    visited.add(nextNode.getKey());
                }
            }
        }
         
        return new Pair<Hashtable<TStation, Integer>, Hashtable<TStation, TStation>>(distances, predecessors);
    }
    
    
    
    /**
     * finds and returns a HashSet of all the shortest paths between desired stations in the represenation of the subway system
     * Makes sure that the shortest paths start and end at the station closest to the user. Uses helper method of 
     * Dijktra's algorithm to do this. Precomputes all the possible paths. While this is not optimal 
     * and an NP hard problem, it is fine in this situatiuon since the number of places currently 
     * are very few
     * 
     * @params startStation     the closest staion to the user
     *         stationsToVisit  the stations on the itinerary of the user (stations closest to the places the user would like
     *                          to visit)
     * @return minPaths, minPathLength      a Pair containing all the path lengths that are equal to the minimum path length                         
     */
    public Pair<HashSet<Vector<TStation>>, Integer> findShortestPath(TStation startStation, ArrayList<TStation> stationsToVisit){   
        
        
        Hashtable<TStation,  Hashtable<TStation, Integer>> pathLengths;
        pathLengths = new Hashtable<TStation,  Hashtable<TStation, Integer>>();
        
        Hashtable<TStation,  Hashtable<TStation, TStation>> paths;
        paths = new Hashtable<TStation,  Hashtable<TStation, TStation>>();
        
        Pair<Hashtable<TStation, Integer>, Hashtable<TStation, TStation>> result = dijkstra(startStation);
        Hashtable<TStation, Integer> distances = result.getKey();
        Hashtable<TStation, TStation> predecessors = result.getValue();
            
        pathLengths.put(startStation, distances);
        paths.put(startStation, predecessors);
        
        //precompute the distances for all stations to visit
        for (TStation stationToVisit : stationsToVisit){
            
            result = dijkstra(stationToVisit);
            distances = result.getKey();
            predecessors = result.getValue();
            
            pathLengths.put(stationToVisit, distances);
            paths.put(stationToVisit, predecessors);
        }
        
        
        //keep track of minimum paths found
        HashSet<Vector<TStation>> minPaths = new HashSet<Vector<TStation>>();
        int minPathLength = Integer.MAX_VALUE;
        int pathLength;
        
        for (List<TStation> possiblePath : pathPermutations(stationsToVisit)){

            Vector<TStation> fullPossiblePath = new Vector<TStation>();
            fullPossiblePath.add(startStation); //always start at start station
            fullPossiblePath.addAll(possiblePath);
            fullPossiblePath.add(startStation); //always end at start station
            
            pathLength = findPathLength(fullPossiblePath, pathLengths);
            
            if (pathLength < minPathLength) {
                minPaths = new HashSet<Vector<TStation>>();
                minPaths.add(fullPossiblePath);
                minPathLength = pathLength;
            } else if (pathLength == minPathLength) { minPaths.add(fullPossiblePath);}
        }
        
        return new Pair<HashSet<Vector<TStation>>, Integer>(minPaths, minPathLength);
    }
    
    
    /** 
     * getter method
     * 
     * return representation    the whole subway system represented using a WeightedAdjustListGraph
     */
    public WeightedAdjustListGraph<TStation> getSubwaySystem(){
        return representation;
    }
    
    /**
     * getter method
     * 
     * @return stations     a Vector of all the stations in the subway system
     * 
     */
    public Vector<TStation> getStations(){
        return representation.getVertices();
    }
    
    /**
     * overriding the toString method
     * 
     * @return representationString     a redable version of the subway system
     */
    public String toString(){
        return representation.toString();
    }
    
    /**
     * very simple data visulization check
     */
    public static void main(String[] args){
       subwaySystem test = new subwaySystem("subway_system_edges.txt");
       System.out.println(test);
       Pair<TStation, Integer> pair1 = new Pair<TStation, Integer>(new TStation("heart", "blue"), 50);
       Pair<TStation, Integer> pair2 = new Pair<TStation, Integer>(new TStation("diamond", "red"), 100);
       NodeDistComparator comparator = new NodeDistComparator();
       System.out.println(comparator.compare(pair1, pair2));
       System.out.println(comparator.compare(pair2, pair1));
    }
}
