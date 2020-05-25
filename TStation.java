
/**
 * T_station represents stops in the MBTA subway system
 * the files provided by the MBTA system were not able to provide 
 * the direction and connection between stops, so it reads in the self made 
 * file. Stations are supposed to represent nodes on the graph of the subway. 
 *
 * @author Taylor Burke
 * @version 21 May 2020
 */
import java.util.Arrays;

public class TStation{
    //same names but differentiated by the color they run on
    //to help with the transfers
    private String station_name;
    private String line;
    
    /**
     * Constructor for objects of class t_station
     * 
     * @params station_name     the stations name
     *         line             the color that the station lies on (ex: green line, red line), can be changed
     *                          for other subway stations where the lines are different (ex: NY uses the alphabet
     *                          instead of colors)
     */
    public TStation(String station_name, String line){
        this.station_name = station_name;
        this.line = line;
    }
    
    /** 
     * getter method
     * 
     * @return station_name     the station name
     */
    public String getStationName(){
        return station_name;
    }
    
    /** 
     * getter method
     * 
     * @return line     the line identifier 
     */
    public String getLine(){
        return line;
    }
    
    /**
     * overriding the toString method
     * 
     * @return      a redable version of the station name with the line in parenthesis
     *              ex: Park(red)
     */
   
    public String toString(){
        return station_name + "(" + line + ")";
    }
    
    /**
     * overriding the equals method
     * 
     * @return  boolean     true if the two objects are the same
     *                      false otherwise
     */
    public boolean equals(Object other){
        if (other == null){return false;}
        if (!TStation.class.isAssignableFrom(other.getClass())){return false;}
        final TStation otherT =  (TStation) other;
        
        if (this.getStationName().equals(otherT.getStationName()) && 
            this.getLine().equals(otherT.getLine())){
                return true;
        } 
        return false;
    }
    
    /**
     * overriding the hashCode method
     * 
     * @return hashCode     how this class should be hashed and looked up after hashing
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{new String(this.station_name), new String(this.line)});
    }
}
