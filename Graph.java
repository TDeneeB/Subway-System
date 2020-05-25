
//*******************************************************************
//  Graph.java    Author: Takis Metaxas
//  Modified: Taylor Burke
//  Defines a basic interface for an directed graph data structure.
//  Modified to suit the MBTA project
//*******************************************************************
import java.util.*;

public interface Graph<T>{
   /** Returns true if this graph is empty, false otherwise. */
   public boolean isEmpty();
   
   /** Returns the number of vertices in this graph. */
   public int getNumVertices();

   /** Returns true if an edge exists between two given vertices
   * which means that two corresponding arcs exist in the graph */
   public boolean isEdge (T vertex1, T vertex2);

   /** Adds a vertex to this graph, associating object with vertex.
   * If the vertex already exists, nothing is inserted. */
   public void addVertex (T vertex);

   /** Inserts an edge between two vertices of this graph,
   * if the vertices exist. Else does not change the graph. */
   public void addEdge (T vertex1, T vertex2, String type, int time);
   
   /** Returns a string representation of the adjacency list. */
   public String toString();
  
}

