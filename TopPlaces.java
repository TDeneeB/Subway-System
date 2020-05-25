
/**
 * TopPlaces is a pseudo "database"/ hardcoded hashtable
 * to represent the top 30 things to do in boston and where the closest T stop is
 * all places are taken from URL: 
 * https://www.timeout.com/boston/things-to-do/50-best-things-to-do-in-boston
 * 
 * Note: feel free to edit the file "places.txt" to add more things to do and personalize 
 * the experience.The set up of the file can be seen in the constructor
 * 
 * @author Taylor Burke
 * @version 21 May 2019
 */

import java.util.Hashtable;
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.util.Scanner;
import java.util.Enumeration;
import java.util.ArrayList;

public class TopPlaces{
    // instance variables
    public static Hashtable<Place,String> places;
    private int numPlaces;
    private ArrayList<Place> allPlaces;
    
    /**
     * Constructor for objects of class places
     */
    public TopPlaces(){
        this.places = new Hashtable<Place,String>();
        this.allPlaces = new ArrayList<>();
        this.numPlaces = 0;
        
        try{
            Scanner scan = new Scanner(new FileReader("places.txt")); 
            
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                String[] info = line.split("~");
                String name = info[1];
                String what = info[2];
                String why = info[3];
                String stop = info[4];
                
                Place nextPlace = new Place(name, what, why, stop);
                places.put(nextPlace, stop);

                this.numPlaces++;
                allPlaces.add(nextPlace);
            }
        } catch (FileNotFoundException exp){
            System.out.println(exp);
        }
        
    }
    
    /**
     * getter method 
     * 
     * @return places   the hastable of a place and the name of the closest TStation
     */
    public static Hashtable<Place,String> getHashPlaces(){
        return places;
    }
    
    /**
     * getter method
     * 
     * @return numPlaces    the integer value for the number of places that the user
     *                      can choose from
     */
    public int getNumPlaces(){
        return numPlaces;
    }
    
    /**
     * overriding the to string method
     * 
     * @return places   a printable string version of the hashtable
     */
    public String toString(){
        return places.toString();
    }
    
    /**
     * getter method
     * 
     * @return  allPlaces   an array containing all the places that are able to be visited
     */
    public ArrayList<Place> getAllPlaces(){
        return allPlaces;
    }
    
    /**
     * for simple testing methods
     */
    public static void main(String[] args){
        TopPlaces test = new TopPlaces();
        //System.out.println(test);
        //System.out.println(test.getNumPlaces());
        System.out.println(test.getHashPlaces());
    }
}
