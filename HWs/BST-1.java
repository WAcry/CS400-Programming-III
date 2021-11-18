/**
 * BST.java created by Ziyuan Zhang on Lenovo Yoga 720 in p2
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      2/28/2020
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Defines the operations required of student's BST class.
 *
 * NOTE: There are many methods in this interface
 * that are required solely to support gray-box testing
 * of the internal tree structure.  They must be implemented
 * as described to pass all grading tests.
 *
 * @author Deb Deppeler (deppeler@cs.wisc.edu)
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 */
public class BST<K extends Comparable<K>, V> implements STADT<K, V> {

  private Node root; // root of the tree
  private int size = 0; // size of the tree

  /**
   * inner class for tree node
   */
  private class Node {

    private K key; // node key
    private V value; // node value
    private Node left; // left child node of node
    private Node right; // right child node of node

    /**
     * a constructor of node
     * @param key // key of the node
     * @param value // value with the key of the node
     * @throws IllegalNullKeyException when key is null
     */
    public Node(K key, V value) throws IllegalNullKeyException {
      if (key == null) {
        throw new IllegalNullKeyException("the key is null");
      }
      this.key = key;
      this.value = value;
      this.left = null;
      this.right = null;
    }

  }


  /**
   * Returns the number of key,value pairs in the data structure
   * @return size of the tree
   */
  public int numKeys() {
    return size;
  }

  /**
   * Add the key,value pair to the data structure and increase the number of keys. If key is null,
   * throw IllegalNullKeyException; If key is already in data structure, throw
   * DuplicateKeyException(); Do not increase the num of keys in the structure, if key,value pair
   * is not added.
   * @param key node key
   * @param value node value
   * @throws IllegalNullKeyException key is null
   * @throws DuplicateKeyException key is duplicate
   */
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    root = insert(key, value, root);

  }

  /**
   * Insert helper
   * @param key node key
   * @param value node value
   * @param node root of a subtree
   * @return proper node
   * @throws IllegalNullKeyException key is null
   * @throws DuplicateKeyException key is duplicate
   */
  private Node insert(K key, V value, Node node)
      throws DuplicateKeyException, IllegalNullKeyException {

    if (key == null) {
      throw new IllegalNullKeyException("key is null");
    }

    // reach the bottom
    if (node == null) {
      size++;
      return new Node(key, value);
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) { // smaller key,->left
      node.left = insert(key, value, node.left);
    } else if (cmp > 0) { // larger key,->right
      node.right = insert(key, value, node.right);
    } else { // the same key existed
      throw new DuplicateKeyException("key is already in data structure");
    }
    return node;

  }


  /**
   * Returns true if the key is in the data structure If key is null, throw
   * IllegalNullKeyException Returns false if key is not null and is not present
   * @throws IllegalNullKeyException if key is null
   */
  public boolean contains(K key) throws IllegalNullKeyException {
    try {
      findNode(key, root);
    } catch (KeyNotFoundException e) {
      return false;
    }
    return true;
  }


  /**
   * Returns the keys of the data structure in pre-order traversal order.
   * In the case of binary search trees, the order is: V L R
   *
   * If the SearchTree is empty, an empty list is returned.
   *
   * @return List of Keys in pre-order
   */
  public List<K> getPreOrderTraversal() {
    List<K> list = new ArrayList<>();
    getPreOrderTraversal(root, list);
    return list;
  }

  /**
   * do PreOrderTraversal
   *
   * @param node the root of a subtree
   * @param list list save the PreOrderTraversal
   */
  private void getPreOrderTraversal(Node node, List list) {
    if (node == null) {
      return;
    }

    list.add(node.key);
    getPreOrderTraversal(node.left, list);
    getPreOrderTraversal(node.right, list);
  }

  /**
   * Returns the keys of the data structure in sorted order.
   * In the case of binary search trees, the visit order is: L V R
   *
   * If the SearchTree is empty, an empty list is returned.
   *
   * @return List of Keys in-order
   */
  public List<K> getInOrderTraversal() {
    List<K> list = new ArrayList<>();
    getInOrderTraversal(root, list);
    return list;
  }

  /**
   * do InOrderTraversal
   *
   * @param node the root of a subtree
   * @param list list save the InOrderTraversal
   */
  private void getInOrderTraversal(Node node, List list) {
    if (node == null) {
      return;
    }

    getInOrderTraversal(node.left, list);
    list.add(node.key);
    getInOrderTraversal(node.right, list);
  }

  /**
   * Returns the keys of the data structure in post-order traversal order.
   * In the case of binary search trees, the order is: L R V
   *
   * If the SearchTree is empty, an empty list is returned.
   *
   * @return List of Keys in post-order
   */
  public List<K> getPostOrderTraversal() {
    List<K> list = new ArrayList<>();
    getPostOrderTraversal(root, list);
    return list;
  }

  /**
   * do PostOrderTraversal
   *
   * @param node the root of a subtree
   * @param list list save the PostOrderTraversal
   */
  private void getPostOrderTraversal(Node node, List list) {
    if (node == null) {
      return;
    }

    getPostOrderTraversal(node.left, list);
    getPostOrderTraversal(node.right, list);
    list.add(node.key);
  }


  /**
   * Returns the keys of the data structure in level-order traversal order.
   *
   * The root is first in the list, then the keys found in the next level down,
   * and so on.
   *
   * If the SearchTree is empty, an empty list is returned.
   *
   * @return List of Keys in level-order
   */
  public List<K> getLevelOrderTraversal() {
    Queue<Node> q = new LinkedList<>();
    List<K> result = new ArrayList<>();
    q.add(root);
    while (!q.isEmpty()) {
      Node cur = q.remove();
      result.add(cur.key);
      if (cur.left != null) {
        q.add(cur.left);
      }
      if (cur.right != null) {
        q.add(cur.right);
      }
    }

    return result;
  }


  /**
   * Returns the height of this BST.
   * H is defined as the number of levels in the tree.
   *
   * If root is null, return 0
   * If root is a leaf, return 1
   * Else return 1 + max( height(root.left), height(root.right) )
   *
   * Examples:
   * A BST with no keys, has a height of zero (0).
   * A BST with one key, has a height of one (1).
   * A BST with two keys, has a height of two (2).
   * A BST with three keys, can be balanced with a height of two(2)
   *                        or it may be linear with a height of three (3)
   * ... and so on for tree with other heights
   *
   * @return the number of levels that contain keys in this BINARY SEARCH TREE
   */
  public int getHeight() {
    return getHeight(root);
  }

  /**
   * get height of a subtree
   *
   * @param node root of a subtree
   * @return height of a subtree
   */
  private int getHeight(Node node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(getHeight(node.left), getHeight(node.right));
  }


  /**
   * Returns the key that is in the root node of this ST. If root is null, returns null.
   *
   * @return key found at root node, or null
   */
  public K getKeyAtRoot() {
    return size == 0 ? null : root.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key.
   * If a matching node is found, it returns the returns the key that is in the left child.
   * If the left child of the found node is null, returns null.
   *
   * @param key A key to search for
   * @return The key that is in the left child of the found key
   *
   * @throws IllegalNullKeyException if key argument is null
   * @throws KeyNotFoundException if key is not found in this BST
   */
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException("key argument is null");
    }

    return findNode(key, root).left == null ? null : findNode(key, root).left.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key.
   * If a matching node is found, it returns the returns the key that is in the right child.
   * If the right child of the found node is null, returns null.
   *
   * @param key A key to search for
   * @return The key that is in the right child of the found key
   *
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException if key is not found in this BST
   */
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException("key argument is null");
    }

    return findNode(key, root).right == null ? null : findNode(key, root).right.key;
  }

  private Node findNode(K key, Node node) throws KeyNotFoundException, IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key is null when get(key)");
    }
    if (node == null) {
      throw new KeyNotFoundException("key is not found");
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      if (node.left != null) {
        return findNode(key, node.left);
      } else {
        throw new KeyNotFoundException("key is not found when get(key)");
      }
    } else if (cmp > 0) {
      if (node.right != null) {
        return findNode(key, node.right);
      } else {
        throw new KeyNotFoundException("key is not found when get(key)");
      }
    } else {
      return node;
    }
  }


  /**
   * Returns the value associated with the specified key.
   *
   * @param key node key
   * @return value corresponding to key
   * @throws IllegalNullKeyException key is null
   * @throws KeyNotFoundException key is not found
   */
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    return findNode(key, root).value;
  }


  /**
   * If key is found, remove the key,value pair from the data structure
   * and decrease num keys, and return true.
   * If key is not found, do not decrease the number of keys in the data structure, return false.
   * If key is null, throw IllegalNullKeyException
   *
   * @param key removed node's key
   * @return if return successfully
   * @throws IllegalNullKeyException key is null
   */
  public boolean remove(K key) throws IllegalNullKeyException {

    if (!contains(key)) {
      return false;
    }

    root = remove(key, root);
    size--;
    return true;
  }

  /**
   * remove helper
   *
   * @param key node key
   * @param node root of a subtree
   * @return the removed node
   * @throws IllegalNullKeyException
   */
  private Node remove(K key, Node node) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key is null when remove(key)");
    }

    // reach bottom
    if (node == null) {
      return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) { // smaller key ->left
      node.left = remove(key, node.left);
      return node;
    } else if (cmp > 0) { // larger key->right
      node.right = remove(key, node.right);
      return node;
    } else { // find the node
      // do deletion
      if (node.left == null) {
        Node rightNode = node.right;
        return rightNode;
      } else if (node.right == null) {
        Node leftNode = node.left;
        node.left = null;
        return leftNode;
      } else {
        Node successor = findSuccessor(node);
        node.value = successor.value;
        node.key = successor.key;
        deleteSuccessor(node);
      }
    }
    return node;
  }

  /**
   * find a successor of a node
   * @param node tree node
   * @return successor of a node
   */
  private Node findSuccessor(Node node) {
    boolean flag = true;
    Node tmp = node.right;
    while (flag) {
      if (tmp.left == null) {
        flag = false;
        break;
      }
      tmp = tmp.left;
    }
    return tmp;
  }

  /**
   * remove the successor after exchange when removing
   * @param node tree node
   */
  private void deleteSuccessor(Node node) {
    boolean flag = true;
    Node tmp = node.right;
    if (tmp.left == null) {
      node.right = null;
      return;
    }

    while (flag) {
      if (tmp.left.left == null) {
        flag = false;
        tmp.left = null;
        break;
      }
      tmp = tmp.left;
    }
  }


  /**
   * Print the tree.
   *
   * For our testing purposes of your print method:
   * all keys that we insert in the tree will have
   * a string length of exactly 2 characters.
   * example: numbers 10-99, or strings aa - zz, or AA to ZZ
   *
   * This makes it easier for you to not worry about spacing issues.
   *
   * You can display a binary search in any of a variety of ways,
   * but we must see a tree that we can identify left and right children
   * of each node
   *
   * For example:
   *
   30
   /\
   /  \
   20  40
   /   /\
   /   /  \
   10  35  50

   Look from bottom to top. Inorder traversal of above tree (10,20,30,35,40,50)

   Or, you can display a tree of this kind.

   |       |-------50
   |-------40
   |       |-------35
   30
   |-------20
   |       |-------10

   Or, you can come up with your own orientation pattern, like this.

   10
   20
   30
   35
   40
   50

   The connecting lines are not required if we can interpret your tree.
   * print the tree
   */
  @Override
  public void print() {
    int treeHeight = getHeight();
    if (treeHeight == 0) {
      return;
    }

    // set array storing the graph
    int arrayHeight = treeHeight * 2 - 1;
    int arrayWidth = (int) ((Math.pow(2, treeHeight - 1)) * 4 - 3);

    // fill the array with space
    String[][] tmp = new String[arrayHeight][arrayWidth];
    for (int i = 0; i < arrayHeight; i++) {
      for (int j = 0; j < arrayWidth; j++) {
        tmp[i][j] = "  ";
      }
    }

    // fill array with proper contents
    arraySaver(tmp, root, 0, tmp[0].length / 2);


    // print the array out
    for (String[] line : tmp) {
      String result = "";
      for (int i = 0; i < line.length; i++) {
        result += line[i];
      }
      System.out.println(result);
    }
  }

  /**
   * print helper
   * @param tmp 2-d array saving graph
   * @param node curren saving node
   * @param rowIndex current row
   * @param colIndex current col
   */
  private void arraySaver(String[][] tmp, BST.Node node, int rowIndex, int colIndex) {
    tmp[rowIndex][colIndex] = String.valueOf(node.key);

    // distance between two elements with 1 difference row
    int distance = (int) Math.pow(2, (getHeight() - rowIndex / 2 - 2));

    // check if a / or \ needed
    if (node.left != null) {
      tmp[rowIndex + 1][colIndex - distance] = " /";
      arraySaver(tmp, node.left, rowIndex + 2, colIndex - distance * 2);
    }
    if (node.right != null) {
      tmp[rowIndex + 1][colIndex + distance] = "\\ ";
      arraySaver(tmp, node.right, rowIndex + 2, colIndex + distance * 2);
    }

  }
}
 // copyrighted material, students do not have permission to post on public sites

//  deppeler@cs.wisc.edu
