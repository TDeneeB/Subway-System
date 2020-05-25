
/**
 * Driver class is the only method that needs to be run in order 
 * for the program to run and to interact with the user to create an itinerary and 
 * tell the user what order the itinerary should be done in in order to maximize their time
 * 
 * note: this can be EASILY expanded to show the exact path, since all the predecessors are returned 
 * from the algorithm, but the scope of this project wasn't to be the next google maps
 * but to give users optional itineraries to maximize their stay in Boston. The city itself
 * can also be changed by updating all the files specifying the edges and nodes of the subway system
 * (these are in txt files: subway_system_edges.txt and places.txt) and changeing the name of the city 
 * printed out to the user! Feel free to add any other top places to the file. 
 * 
 * note: this does use brute force return the fastest path (this needs to be improved 
 * upon if more places are to be added) by using Dijkstra's algorithm as implemented in subwaySystem
 *
 * @author Taylor Burke
 * @version 23 May 2020
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set; 
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Scanner;
import java.util.PriorityQueue;
import javafx.util.Pair; 

public class Driver{
    // instance variables
    private subwaySystem subway;
    private Vector<TStation> stations;
    
    private TStation startStation;
    private ArrayList<Place> placesToVisit;
    private ArrayList<TStation> stationsToVisit;
    
    private HashSet<Vector<TStation>> paths; //optimal (minimum) paths found
    private Integer minPathLength;
    
    /**
     * Constructor for objects of class driver
     * 
     * 1) creates the subway system
     * 2) queries the user for what places they wish to visit and the station closest to them, creating
     * their personal itinerary
     * 3) finds the stations closest to those places they wish to visit
     * 4) finds the shortest path (based on time) between all those things using the subway system
     * 5) tells the user what places should be visited in what order (optional minimal paths)
     *   
     */
    public Driver(){
       //setting up the subway system
       this.subway = new subwaySystem("subway_system_edges.txt");
       this.stations = subway.getStations();
       
       //getting all the stations to visit by querying the user for
       //their itinerary while they are visiting boston
       QueryUser userItinerary = new QueryUser();
       if (userItinerary.getItinerary().size() == 0){
           System.out.println("MMMhhh your itinerary seems empty, better just stay home");
           return; 
       }
              
       //asking the user for the nearest station to them (for a start point)
       this.startStation = getStartStation();
       
       //transforming user information into code ready data
       this.placesToVisit = userItinerary.getItinerary();
       this.stationsToVisit = itineraryToStations(this.placesToVisit);
       
       //find the shortest path they must take to complete their itinerary
       Pair<HashSet<Vector<TStation>>, Integer> result = subway.findShortestPath(this.startStation, this.stationsToVisit);
       this.paths = result.getKey();
       this.minPathLength = result.getValue();
       
       System.out.println(pathsToString(this.paths, this.startStation, this.placesToVisit, this.minPathLength)); //print the optimal paths to the screen
    }
    
    /**
     * helper method to ask the user for the closest station to them
     * 
     * @return the name of the closest station (not including the color if station
     * is apart of multiple lines)
     */
    private TStation getStartStation(){
        System.out.println("Please enter the nearest T station to you");
        System.out.println("Here are all the valid choices:");
        
        Set validStationNames = new HashSet();
        for (TStation station: this.stations){
            String name = station.getStationName();
            validStationNames.add(name);
            System.out.println(name);
        }
        
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        
        //keep asking the user for a valid station until they enter a valid station
        //name
        while (!validStationNames.contains(input)){
            System.out.println("You entered an incorrect T Station.");
            System.out.println("Please refer to the above list for correct station names");
            input = scan.nextLine();
        }
        

        for (TStation station : this.stations){
            if (station.getStationName().equals(input)){
                return station;
            }
        }
        
        return new TStation("wrong input", "wrong input") ;
    }
    
    /**
     * takes the itinerary and turns it into TStations (nodes) that need to be visited
     * while trasversing the subway system and travelling around Boston
     * 
     * @param placesToVisit     an ArrayList of Place objects that are in the user itinerary 
     * @return stationToVisit   an ArrayList of TStation objects that are the closest to the Place objects in the user itinerary 
     */
    private ArrayList<TStation> itineraryToStations(ArrayList<Place> placesToVisit){
        HashSet<TStation> itineraryStations = new HashSet<TStation>();
        Hashtable<Place, String> places = TopPlaces.getHashPlaces();
        
        for( Place placeToVisit :  placesToVisit){
            String stationName = places.get(placeToVisit);
            System.out.println(placeToVisit);
            System.out.println(stationName);
            for (TStation station : this.stations){
                if (station.getStationName().equals(stationName)){
                    itineraryStations.add(station);
                    break; //in case there are multiple stations but different color lines
                }
            }
        }
        return new ArrayList<TStation>(itineraryStations);
    }
    
    
    /**
     * Turns all the information about stations and paths into a string format to be printed to the user
     * Tells the user all the possible paths that are equal to the minimum path length, all of which was found 
     * using Dijkstra's algorithm and how long it is estimated to take.
     * 
     * @param paths             a HashSet of Vectors that contain all the stations (in order of visitation) that are equal to the minimum path length
     *        startStation      The closest TStation to the user, as indicated by the user
     *        placesToVisit     an ArrayList of Place objects that are in the user itinerary  
     *        minPathLength     the time it is estimated that each minimum path will take on the subway station
     */
    private String pathsToString(HashSet<Vector<TStation>> paths, TStation startStation, ArrayList<Place> placesToVisit, Integer minPathLength){
        Hashtable<String, ArrayList<Place>> stationNameToPlace = new Hashtable<String, ArrayList<Place>>();
        Hashtable<Place, String> placeToStation = TopPlaces.getHashPlaces();

        for (Place place : placeToStation.keySet()){
            String stationName = placeToStation.get(place);
            if (!stationNameToPlace.containsKey(stationName)){
                stationNameToPlace.put(stationName, new ArrayList<Place>());
            }
            stationNameToPlace.get(stationName).add(place);
        }
        
        String startStationName = startStation.getStationName();
        if (!stationNameToPlace.containsKey(startStationName)){
            stationNameToPlace.put(startStationName, new ArrayList<Place>());
        }
        
        Place homePlace = new Place(startStationName, "closest station", "start & end here",  startStationName);
        stationNameToPlace.get(startStationName).add(homePlace);
        placesToVisit.add(homePlace);
        
        String pathsString = "\nYou're itinerary is complete!\n";
        pathsString += "\nThe optimal path(s) between all the places on your itineray has been found, minimizing your time of travel.";
        pathsString += "Your itinerary shows that you will be spending approximately ";
        
        int i = 1;
        ArrayList<Place> mappedPlaces;
        for (Vector<TStation> path : paths){
            if (i == 1) {
                pathsString += "\n" + String.valueOf(minPathLength) + " minutes on the MBTA subway system using " + 
                                String.valueOf(path.size()-1) + " T stations."; 
                pathsString += "\n\nHere are your possible itineraries: ";
            }
            
            pathsString += "\n\n Path #" + String.valueOf(i);
            
            int j = 1;
            for (TStation station : path) {
                
                String stationName = station.getStationName();
                mappedPlaces = stationNameToPlace.get(stationName);
                
                for (Place place : mappedPlaces){
                    if (placesToVisit.contains(place)){
                        pathsString += "\n(" + String.valueOf(j) + ") " + place;
                        j++;
                    }
                    
                }
            }
            pathsString += "\n";
            i++;
            if (i==6) break; //only show the top 5 paths
        }
        
        return pathsString;
    }
    
    /**
     * Initialize everything and run the program to interact with the user
     */
    public static void main(String[] args){
        Driver test = new Driver();
    }
}
