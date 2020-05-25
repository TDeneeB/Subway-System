/********************************************************************
  * WeightedAdjustListGraph.java @version 2018.11.08
  * Implementation of the Graph.java interface using Lists of
  * Adjacent nodes, corresponding edges are weighted
  * 
  * Modified by Taylor Burke for project purposes (18 May 2020) to be weighted and bidirectional
  ********************************************************************/

import java.util.LinkedList;
import java.util.Vector;
import java.util.ArrayList;
import java.io.*;
import java.util.Hashtable;

public class WeightedAdjustListGraph<T> implements Graph<T>{
  private final int NOT_FOUND = -1;
  private Vector<LinkedList<Edge<T>>> connections;   // adjacency list
  private Vector<T> vertices;   // values of vertices
  
  /******************************************************************
    * Constructor. Creates an empty graph.
    ******************************************************************/
  public WeightedAdjustListGraph() {
    this.connections = new Vector<LinkedList<Edge<T>>>();
    this.vertices = new Vector<T>();
  }
    
  /******************************************************************
    * Returns true if the graph is empty and false otherwise. 
    ******************************************************************/
  public boolean isEmpty() {
    return vertices.size() == 0;
  }
  
  /******************************************************************
    * Returns the number of vertices in the graph.
    ******************************************************************/
  public int getNumVertices() { 
    return vertices.size(); 
  }
  
  /******************************************************************
    * Returns the number of connections in the graph by counting them.
    ******************************************************************/
  public int getNumArcs() {
    int totalArcs = 0;
    for (int i = 0; i < vertices.size(); i++) //for each vertex
      //add the number of its connections
      totalArcs = totalArcs + connections.get(i).size(); 
    
    return totalArcs; 
  }
  
  /******************************************************************
    * Returns true if a bi-directional edge exists from station1 and station2
    ******************************************************************/
  public boolean isEdge (T station1, T station2){ 
    try {
      int index = vertices.indexOf(station1);
      LinkedList<Edge<T>> l = connections.get(index);
      return (l.indexOf(station2) != -1);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("one station does not belong in the graph");
      return false;
    }
  }
  
  /******************************************************************
    * Returns the time between each station
    ******************************************************************/
  public int getEdgeWeight(T station1, T station2){
     try {
          int index = vertices.indexOf(station1);
          LinkedList<Edge<T>> l = connections.get(index);
          Edge connection = l.get(l.indexOf(station2));
          return connection.getTime();
      } catch (ArrayIndexOutOfBoundsException e) {
          System.out.println("start station does connect to end station");
          return 0;
     }
  }
  
 /******************************************************************
   Adds a vertex to the graph, expanding the capacity of the graph
   if necessary.  If the vertex already exists, it does not add it.
   ******************************************************************/
  public void addVertex (T station) {
    boolean seen = false;
    for (T t: vertices){
        if (t.equals(station)){
            seen = true;
            return;
        }
    }
    
    
    if (!seen){
      //the vertex is not already there
      // add it to the vertices vector
      vertices.add(station); 
      //indicate that the new vertex has no connections to other vertices yet
      connections.add(new LinkedList<Edge<T>>()); 
    }
  }

  /******************************************************************
    * Inserts an edge between two vertices of the graph.
    * If one or both vertices do not exist, ignores the addition.
    ******************************************************************/
  public void addEdge (T vertex1, T vertex2, String type, int time) {
    this.addArc (vertex1, vertex2, type, time);
    this.addArc (vertex2, vertex1, type, time);
  }
  
  /******************************************************************
    * Inserts an arc from v1 to v2.
    * If the vertices exist, else does not change the graph. 
   ******************************************************************/
  public void addArc (T source, T destination, String type, int time){
    int sourceIndex = vertices.indexOf(source);
    int destinationIndex = vertices.indexOf(destination);
    Edge edgeBetween = new Edge(source, destination, type, time);
    //if source and destination exist, add the arc. do nothing otherwise
    if ((sourceIndex != -1) && (destinationIndex != -1)){
      LinkedList<Edge<T>> l = connections.get(sourceIndex);
      l.add(edgeBetween);
      }
    }
  
    /**
     * getter method
     * 
     * @return vertices     all the vertices in the graph as a vector
     */
    public Vector<T> getVertices(){
        return vertices;
    }
    
    /**
     * Retrieve from a graph the vertices adjacent to vertex v.
     * Assumes that the vertex is in the graph.
     */
    public LinkedList<Edge<T>> getSuccessors(T vertex) {
        int indx = this.vertices.indexOf(vertex);
        return this.connections.get(indx);
    }

      /******************************************************************
       Returns a string representation of the graph. 
       ******************************************************************/
      public String toString() {
        if (vertices.size() == 0) 
            return "Graph is empty";
        
        String result = "Vertices: \n";
        result = result + vertices;
        
        result = result + "\n\nEdges: \n";
        for (int i=0; i< vertices.size(); i++)
          result = result + "from " + vertices.get(i) + ": "  + connections.get(i)+ "\n";
       
        return result;
      }
 
}

