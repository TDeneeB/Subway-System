
/**
 * Query User is a way to interact with the user and store all the things that the 
 * user would like to do during their time in Boston.
 * It lists the things out and allows the user to interact with the details
 * it creates to itinerary for the user
 *
 * @author Taylor
 * @version 18 May 2020
 */

import java.util.ArrayList;
import java.util.Scanner; 
import java.lang.StringBuilder;
import javafoundations.exceptions.InvalidInputException;

public class QueryUser{
    // instance variables
    private ArrayList<Place> itinerary;
    private ArrayList<String> itineraryStations;
    private String instructions;
    
    /**
     * Constructor for objects of class QueryUser
     * 
     * @throws exception    InvalidInputException during the process inorder to make sure the user is following directions
     * 
     */
    public QueryUser() throws InvalidInputException{
        //setting up all the variables used
        TopPlaces Places = new TopPlaces();
        ArrayList<Place> allPlaces = Places.getAllPlaces();
        
        this.itinerary = new ArrayList<Place>();
        int numPlaces = Places.getNumPlaces();
        
        Scanner scan = new Scanner(System.in);
        StringBuilder instructions = createInstructions();
        //this.itineraryStations = new ArrayList<String>();
        
        //printing out the introduction and instructions for the user
        System.out.println(createIntroduction(allPlaces, numPlaces));
        System.out.println(instructions);
        
        //building the users itinerary until the user says to stop
        String input = scan.nextLine();
        while (!input.equalsIgnoreCase("stop")){
            try{
               String[] commands = input.split(" ");
               int numCommands = commands.length;
               
               //when a single command is entered
               if (numCommands == 1){
                   String command = commands[0];
                   if (command.equalsIgnoreCase("all")){
                       for (Place aPlace: allPlaces){
                           if (!itinerary.contains(aPlace)){
                               itinerary.add(aPlace);
                           }
                        }
                    } else if (command.equalsIgnoreCase("places")){
                       for (int i = 0; i < numPlaces; i++){
                           Place thisPlace = allPlaces.get(i);
                           System.out.println(i + 1 + ": " + thisPlace.getName());
                        }
                    } else if (command.equalsIgnoreCase("itinerary")){
                        System.out.println("Itinerary:");
                        for (int i = 0; i < itinerary.size(); i++){
                            System.out.println("-" + itinerary.get(i));
                        }
                   } else {
                       throw new InvalidInputException("single command entered wrong");
                   }
               //when a two part command is entered    
               }else if (numCommands == 2){
                   int locationNum = Integer.valueOf(commands[1]) - 1;
                   //first checks if the number given is in range
                   if (!(0 <= locationNum && locationNum <= numPlaces)){
                       throw new InvalidInputException("number wrong");
                   }
                   //then checks if the command given is valid
                   //if it is valid it will perform the command
                   //if not, it will print the instructions again
                   String command = commands[0];
                   Place location = allPlaces.get(locationNum);
                   
                   //printing the what information
                   if (command.equalsIgnoreCase("what")){
                       System.out.println(location.getWhat());
                    //printing the why information
                    } else if (command.equalsIgnoreCase("why")){
                        System.out.println(location.getWhy());
                    //adding the place to the itinerary
                    } else if (command.equalsIgnoreCase("add")){
                        //only adds if the itinerary does not contain the location yet
                        if (!itinerary.contains(location)){
                            itinerary.add(location);
                        }
                    //removing the place from the itinerary
                    } else if (command.equalsIgnoreCase("remove")){
                        //only deletes if the itinerary does contain the location
                        if (itinerary.contains(location)){
                            itinerary.remove(location);
                        }
                    //when the command does not match any of the valid commands
                    } else {
                        throw new InvalidInputException("first command wrong");
                    }
                    
               //when no commands are entered
               } else {
                   throw new InvalidInputException("no commands entered");
               }
                              
            } catch (InvalidInputException exp){
                System.out.println("OOPS! Looks like you entered an invalid comand");
                System.out.println("Please read the following instructions:");
                System.out.println(instructions);
            } catch (Exception e){
                System.out.println("OOPS! Looks like you entered an invalid comand");
            }
            input = scan.nextLine();
        
        }
        //System.out.println(itinerary);
    }
    
    /**
     * helper method to create the introduction that will be printed to the user
     * 
     * @return String version of the introduction
     */
    private static StringBuilder createIntroduction(ArrayList<Place> allPlaces, 
                                                                int numPlaces){
        StringBuilder intro = new StringBuilder("WELCOME TO BOSTON!\n\n");
        intro.append("You might have a limited time " +
        "and transportation while in town.\n");
        intro.append("Therefore, this app allows you to create a customized");
        intro.append(" itinerary for all the places that you want to visit.\n");
        intro.append("From there, it will give you a detailed itinerary for how");
        intro.append(" to get around the city using the subway system.\n\n");
        intro.append("Here are some of the TOP things you can choose from:\n");
        
        for (int i = 0; i < numPlaces; i++){
            Place thisPlace = allPlaces.get(i);
            intro.append(i + 1 + ": " + thisPlace.getName() + "\n");
        }

        return intro;
    }
    
    /**
     * helper method to create the instructions that will be printed to the user
     * 
     * @return String version of the instructions
     */
    private static StringBuilder createInstructions(){
        StringBuilder instr = new StringBuilder("If you would like to build an "+
        "itinerary for your stay in Boston, \n" + 
        "use the following commands followed by the number associated with " +
        "the desired location:\n\n");
        instr.append("'Why': This will print out why this spot makes the top list" + 
        " of things to do in Boston\n");
        instr.append("'What': This will print out what you can do at this spot\n");
        instr.append("'Add': This will add the location to your itinerary\n");
        instr.append("'Remove': This will delete the location from you itinerary\n\n");
        instr.append("For example: 'Why 1' will print why #1 on the list is a");
        instr.append(" recommended place to visit.\n\n");
        instr.append("Single additional commands may also be used:\n\n");
        instr.append("'All': This will add all the stops to your itinerary\n");
        instr.append("'Stop': This will stop the program from building your itinerary.\n");
        instr.append("'Itinerary': This will print your itinerary so far.\n");
        instr.append("'Places': This will print the top places again.\n");
        return instr;
    }
        
    
    /**
     * getter method
     * 
     * @return itinerary    the array list of all the places that the user wants to visit
     *                      while the user is in town
     * 
     */
    public ArrayList<Place> getItinerary(){
        return itinerary;
    }
    
    /**
     * getter method
     * 
     * @return itinararyStation     the array list of all the places that the user wants to visit
     *                              while the user is in town, but in the form of the closest stations
     */
    public ArrayList<String> getStationsToVisit(){
        return itineraryStations;
    }
    
    
    /**
     * overriding the toString method
     * 
     * @return a easily readable version of the itinerary
     * 
     */
    public String toString(){
        StringBuilder itin = new StringBuilder("Itinerary:\n");
        for (int i = 0; i < itinerary.size(); i++){
              itin.append(i + 1 + ": " + itinerary.get(i)+ "\n");
        }
        itin.append("Stations needed to stop at:\n");
        for (int i = 0; i < itineraryStations.size(); i++){
              itin.append(i + 1 + ": " + itineraryStations.get(i)+ "\n");
        }
        return itin.toString();
    }
    
    /**
     * for testing
     */
    public static void main(String[] args){
        QueryUser test = new QueryUser();
        System.out.println(test);
    }
}
