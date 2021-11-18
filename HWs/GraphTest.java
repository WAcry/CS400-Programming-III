/**
 * Filename:   GraphTest.java
 * Project:    p4
 * Authors:   Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      4/8/2020
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


import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tester class for Graph
 *
 * @author Ziyuan Zhang
 */
public class GraphTest {

  Graph graph;

  /**
   * Run before each test
   */
  @BeforeEach
  void setUp() {
    graph = new Graph();
  }


  /**
   * Add a few vertices and edges
   */
  @Test
  void testGraph_001_add_vertices_and_edges() {
    try {
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addEdge("A", "B");
      graph.addEdge("B", "A");
      graph.addEdge("B", "C");
      graph.addEdge("B", "C");
      HashSet<String> set = new HashSet<>();
      set.add("A");
      set.add("C");
      set.add("B");
      assert graph.getAllVertices().equals(set);
      assert graph.order() == 3;
      assert graph.size() == 3;
    } catch (Exception e) {
      fail("Unexpected Exception Caught");
    }

  }


  /**
   * Test function of removing vertices and edges
   */
  @Test
  void testGraph_002_remove_vertices_and_edges() {
    try {
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addEdge("A", "B");
      graph.addEdge("B", "A");
      graph.addEdge("B", "C");
      graph.addEdge("A", "C");

      graph.removeVertex("D");
      graph.removeEdge("B", "D");
      graph.removeVertex("B");
      assert graph.order() == 2;
      assert graph.size() == 1;

      graph.removeEdge("C", "A");
      graph.removeEdge("A", "C");
      assert graph.order() == 2;
      assert graph.size() == 0;

      ArrayList<String> list = new ArrayList<>();
      assert graph.getAdjacentVerticesOf("B").equals(list);


    } catch (Exception e) {
      fail("Unexpected Exception Caught");
    }

  }

  /**
   * Check if any problems when add null vertex or edge with null vertex
   */
  @Test
  void testGraph_003_add_null_vertex_and_null_edge() {
    graph.addVertex(null);
    assert graph.order() == 0;
    graph.addEdge(null, "A");
    assert graph.size() == 0;
  }

  /**
   * Check if any the graph works as expected when add duplicated vertex or edge
   */
  @Test
  void testGraph_004_add_duplicated_vertex_and_edge() {
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("A");
    graph.addEdge("B", "A");
    graph.addEdge("B", "A");
    graph.addEdge("A", "B");
    assert graph.size() == 2;
    assert graph.order() == 2;
  }

  /**
   * Check if any the graph works as expected when remove null vertex or edge with null vertex
   */
  @Test
  void testGraph_005_remove_null_vertex_and_edge() {
    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "C");
    graph.removeVertex(null);
    graph.removeEdge("B", null);
    assert graph.size() == 3;
    assert graph.order() == 3;
  }
}
