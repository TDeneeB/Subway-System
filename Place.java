
/**
 * A place represents a location to in the specified city (for this project it was Boston/Cambridge)
 * It is many descriptors to be as specific as possible when interacting with the user such as
 * the closest public transit stop, what the location is, and why the location should be visited
 *  
 * @author Taylor Burke
 * @version 21 May 2019
 */
import java.util.Arrays;

public class Place{
    // instance variables
    private String name;
    private String what;
    private String why;
    private String stop;

    /**
     * Constructor for objects of class place
     * 
     * @param name      the name of the place
     *        what      the description of what it is
     *        why       why the location is on the list
     *        stop      the cloests subway stop name but in string format
     */
    public Place(String name, String what, String why, String stop){
        this.name = name;
        this.what = what;
        this.why = why;
        this.stop = stop;
    }
    
    /**
     * a getter method
     * 
     * @return name     the name of the place
     */
    public String getName(){
        return name;
    }
    
    /**
     * a getter method
     * 
     * @return what      the description of what it is
     */
    public String getWhat(){
        return what;
    }
    
    /**
     * a getter method
     * 
     * @return why the location is on the list
     */
    public String getWhy(){
        return why;
    }
    
    /**
     * getter method. 
     * 
     * @return stop      the cloests subway stop name but in string format
     * 
     */
    public String getStop(){
        return stop;
    }
    
    /**
     * overriding the string method
     * 
     * @return a string version to print 
     */
    public String toString(){
        return name;
    }
    
    /**
     * overriding the equals method
     * 
     * @return boolean      true if the objects are equal on all aspects
     *                      false otherwise
     */
    public boolean equals(Object other){
        if (other == null){return false;}
        if (!Place.class.isAssignableFrom(other.getClass())){return false;}
        final Place otherP =  (Place) other;
        
        if (this.getName().equals(otherP.getName()) &&
            this.getWhat().equals(otherP.getWhat()) &&
            this.getWhy().equals(otherP.getWhy())   &&
            this.getStop().equals(otherP.getStop())){
                return true;
        }
        
        return false;
    }
    
    /**
     * overriding the hashCode method
     * 
     * @returns hashCode      specifies how an object of this class should be hashed and looked up 
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{new String(this.getName()), new String(this.getWhat()), 
                                            new String(this.getWhy()), new String(this.getStop())});
    }
}
