
/**
 * Edge is a representation of a weighted edge between two nodes
 * this is to allow the graphs to be "travel time" between each stop
 * along the MBTA system. Edges are direct and double sided (time there is 
 * equivilent to time back). 
 *
 * @author Taylor Burke
 * @version 19 May 2020
 */
public class Edge<T> implements Comparable<Edge<T>>{
    // instance variables
    private T stop1;
    private T stop2;
    private String type;
    private int time;
    
    /**
     * Constructor for objects of class edge
     * 
     * @params stop1    a stop at one end of the edge
     *         stop2    a stop at the other end of the edge 
     *         type     the type of path between the two stops (ex:foot (if transfer), subway, ferry, bus) 
     *         time     weight of the edge, the time it would take to travel from one end to the next.
     */
    public Edge(T stop1, T stop2, String type, int time){
        this.stop1 = stop1;
        this.stop2 = stop2;
        this.type = type;
        this.time = time;
    }
    
    /**
     * getter method
     * 
     * @return stop1    a stop at one end of the edge
     */
    public T getStop1(){
        return stop1;
    }
    
    /**
     * getter method
     * 
     * @return stop2    a stop at the other end of the edge 
     */
    public T getStop2(){
        return stop2;
    }
    
    /** 
     * getter method
     * 
     * @return stops    string version of the the stops/ vertices that are connected via this edge
     *                 
     */
    public String getStops(){
        return stop1 +  ",  " + stop2;
    }
    
    /**
     * getter method
     * 
     * @return time     the time that it would take to transverse the edge (i.e. the edge weight)
     */
    public int getTime(){
        return time;
    }
    
    /** 
     * getter method
     * 
     * @return type     the type of path between the two stops (ex:foot (if transfer), subway, ferry, bus) 
     */
    public String getType(){
        return type;
    }
    
    /**
     * overriding the toString methog
     * 
     * @return edgeString   a redable version of the date with the stops, type, and time in parenthesis
     * 
     */
    public String toString(){
        return "(" + this.getStops() + ", " + this.getType() +
        ", " + this.getTime() + ")";
    }
    
    /**
     * overriding the compare to method
     * 
     * edges are only compared based on the times
     * 
     * @param another edge object to compare to
     * @return an integer denoting comparision (0 if equal, - if this is less, + 
     * if this is more)
     */
    public int compareTo(Edge<T> other){
        if (this.time < other.getTime()){
            return -1;
        } else if (other.getTime() < this.time){
            return 1;
        } else {
            return 0;
        }
    }
}
