/**
 * Graph.java created by Ziyuan Zhang on Lenovo Yoga 720 in p4
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      4/2/2020
 *
 * Course:    CS400
 * Semester:  Spring 2020
 * Lecture:   001
 *
 * IDE:       Intellij IDEA IDE for Java Developers
 * Version:   2019.3.2
 * Build id:  193.6015.39
 *
 * Device:    Ziyuan-Yoga720
 * OS:        Windows 10 Home
 * Version:   1903
 * OS build:  18362.535
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Ziyuan Zhang
 *
 * Directed and unweighted graph implementation
 */
public class Graph implements GraphADT {

  private HashMap<String, Vertex> vertices;
  int size; // number of edges in the graph
  int order; // number of vertices in graph

  /**
   * no-arg constructor for a directional Graph object
   */
  public Graph() {
    vertices = new HashMap<>();
    size = 0;
    order = 0;
  }


  /**
   * An inner class for vertices in the graph
   */
  static class Vertex {

    String label; // vertex content
    ArrayList<Edge> edges; // all edges connected with this vertex

    /**
     * A constructor for Vertex setting the String type content by label
     * @param label content of a vertex
     */
    private Vertex(String label) {
      this.label = label;
      edges = new ArrayList<>();
    }
  }


  /**
   * An inner class for directional edges in the graph
   */
  static class Edge {

    String src; // source of the edge
    String tar; // target of the edge

    /**
     * A constructor setting source and target
     * @param src source of the edge
     * @param tar target of the edge
     */
    private Edge(String src, String tar) {
      this.src = src;
      this.tar = tar;
    }
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists,
   * method ends without adding a vertex or
   * throwing an exception.
   *
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph
   *
   * @param vertex the vertex to be added
   */
  @Override
  public void addVertex(String vertex) {
    if (!(vertex == null) && !vertices.containsKey(vertex)) {
      vertices.put(vertex, new Vertex(vertex));
      order++;
    }
  }


  /**
   * Remove a vertex and all associated
   * edges from the graph.
   *
   * If vertex is null or does not exist,
   * method ends without removing a vertex, edges,
   * or throwing an exception.
   *
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph
   *
   * @param vertex the vertex to be removed
   */
  @Override
  public void removeVertex(String vertex) {
    if (!(vertex == null) && vertices.containsKey(vertex)) {
      // remove all connected edge first
      List<String> list = getAdjacentVerticesOf(vertex);
      for (String v : list) {
        removeEdge(vertex, v);
        removeEdge(v, vertex);
      }
      // remove the vertex
      vertices.remove(vertex);
      order--;
    }
  }

  /**
   * Add the edge from vertex1 to vertex2
   * to this graph.  (edge is directed and unweighted)
   *
   * If either vertex does not exist,
   * VERTEX IS ADDED and then edge is created.
   * No exception is thrown.
   *
   * If the edge exists in the graph,
   * no edge is added and no exception is thrown.
   *
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph
   * 3. the edge is not in the graph
   *
   * @param vertex1 the first vertex (src)
   * @param vertex2 the second vertex (dst)
   */
  @Override
  public void addEdge(String vertex1, String vertex2) {
    // add the vertex if not exist first
    addVertex(vertex1);
    addVertex(vertex2);

    // check if the edge already existed
    for (Edge tempEdge : vertices.get(vertex1).edges) {
      // if the edge is found
      if (tempEdge.src.equals(vertex1) && tempEdge.tar.equals(vertex2)) {
        vertices.get(vertex1).edges.add(new Edge(vertex1, vertex2));
        vertices.get(vertex2).edges.add(new Edge(vertex1, vertex2));
        return;
      }
    }

    // if the edge doesn't exist before
    vertices.get(vertex1).edges.add(new Edge(vertex1, vertex2));
    vertices.get(vertex2).edges.add(new Edge(vertex1, vertex2));
    size++;
  }

  /**
   * Remove the edge from vertex1 to vertex2
   * from this graph.  (edge is directed and unweighted)
   * If either vertex does not exist,
   * or if an edge from vertex1 to vertex2 does not exist,
   * no edge is removed and no exception is thrown.
   *
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph
   * 3. the edge from vertex1 to vertex2 is in the graph
   *
   * @param vertex1 the first vertex
   * @param vertex2 the second vertex
   */
  @Override
  public void removeEdge(String vertex1, String vertex2) {
    // check if two vertices exist
    if (!(vertices.containsKey(vertex1) && vertices.containsKey(vertex1))) {
      return;
    }

    ArrayList<Edge> tempEdges = vertices.get(vertex1).edges;
    for (Edge tempEdge : tempEdges) {
      // if the edge exists
      if (tempEdge.src.equals(vertex1) && tempEdge.tar.equals(vertex2)) {
        vertices.get(vertex1).edges.remove(tempEdge);
        vertices.get(vertex2).edges.remove(tempEdge);
        size--;
        break;
      }
    }

  }

  /**
   * Returns a Set that contains all the vertices
   *
   * @return a Set<String> which contains all the vertices in the graph
   */
  @Override
  public Set<String> getAllVertices() {
    return new HashSet<>(vertices.keySet());
  }

  /**
   * Get all the neighbor (adjacent-dependencies) of a vertex
   *
   * For the example graph, A->[B, C], D->[A, B]
   *     getAdjacentVerticesOf(A) should return [B, C].
   *
   * In terms of packages, this list contains the immediate
   * dependencies of A and depending on your graph structure,
   * this could be either the predecessors or successors of A.
   *
   * @param vertex the specified vertex
   * @return an List<String> of all the adjacent vertices for specified vertex
   */
  @Override
  public List<String> getAdjacentVerticesOf(String vertex) {
    ArrayList<String> result = new ArrayList<>();
    if (vertex == null || !vertices.containsKey(vertex)) {
      return result; // return empty list if vertex does not exist
    }

    ArrayList<Edge> tempEdges = vertices.get(vertex).edges; // list of edges of the vertex
    for (Edge tempEdge : tempEdges) { // iterate the edge list to find adjacent vertices
      // add the side of the edges which is not vertex to the "adjacent vertices result-list"
      if (tempEdge.src.equals(vertex)) {
        result.add(tempEdge.tar);
      } else {
        result.add(tempEdge.src);
      }
    }

    return result;
  }

  /**
   * Returns the number of edges in this graph.
   * @return number of edges in the graph.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns the number of vertices in this graph.
   * @return number of vertices in graph.
   */
  @Override
  public int order() {
    return order;
  }

}
