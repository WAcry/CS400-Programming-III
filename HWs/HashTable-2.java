/**
 * HashTable.java created by Ziyuan Zhang on Lenovo Yoga 720 in p3 HashTable
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      3/9/2020
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


/**
 * A generic implementation of a HashTableADT class
 *
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 * @author Ziyuan Zhang
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  private int numKeys; // elements number of the hashtable
  private int capacity; // the size/capacity of the hashtable
  private double loadFactorThreshold; // the max ratio between numKeys and capacity

  private static final int DEFAULT_CAPACITY = 11;
  private static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;
  private static final int COLLISION_RESOLUTION = 5; // array of linked nodes implementation

  private LinkedNode<K, V>[] table; // the table bucket storing linked node elements


  /**
   * A inner generic class of a linked node storing in the buckets.
   *
   * @param <K> A Comparable type to be used as a key to an associated value.
   * @param <V> A value associated with the given key.
   */
  private static class LinkedNode<K, V> {

    K key; // key to an associated value
    V value; // value associated with the given key
    LinkedNode<K, V> next; // next linked node
    LinkedNode<K, V> last; // last linked node

    /**
     * A constructor for a linked node with key and value as args
     *
     * @param key   key to an associated value
     * @param value value associated with the given key
     */
    private LinkedNode(K key, V value) {
      this.key = key;
      this.value = value;
      next = null;
      last = null;
    }
  }

  /**
   * A default no-arg constructor for the hashtable
   */
  public HashTable() {
    numKeys = 0;
    this.capacity = DEFAULT_CAPACITY;
    table = new LinkedNode[DEFAULT_CAPACITY];
    this.loadFactorThreshold = DEFAULT_LOAD_FACTOR_THRESHOLD;
  }


  /**
   * A constructor for the hashtable accepts initial capacity and load factor threshold as args
   *
   * @param initialCapacity     initial hashtable size for storing elements
   * @param loadFactorThreshold the max ratio between numKeys and capacity, decides when to resize
   *                            and rehash
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    numKeys = 0;
    this.capacity = initialCapacity;
    table = new LinkedNode[initialCapacity];
    this.loadFactorThreshold = loadFactorThreshold;
  }

  /**
   * Get load factor threshold
   *
   * @return load factor threshold
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Get load factor
   *
   * @return load factor
   */
  @Override
  public double getLoadFactor() {
    return numKeys / (double) capacity;
  }

  /**
   * Get capacity of the current hashtable
   *
   * @return capacity of the current hashtable
   */
  @Override
  public int getCapacity() {
    return capacity;
  }

  /**
   * Get the integer code indicating which collision resolution strategy is used
   *
   * @return an integer code indicating which collision resolution strategy is used
   */
  @Override
  public int getCollisionResolution() {
    return COLLISION_RESOLUTION;
  }

  /**
   * Insert a new key with a value into the hashtable. If the key is duplicated, the old value would
   * be covered by new value. The key cannot be null
   *
   * @param key   key to an associated value
   * @param value value associated with the given key
   * @throws IllegalNullKeyException when key is null
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key should not be null when insert");
    }

    int index = Math.abs(key.hashCode() % capacity); // index must not be negative

    if (table[index] == null) { // empty bucket index
      table[index] = new LinkedNode<>(key, value);
      numKeys++;
    } else { // the bucket index is not empty, need to connect as a linked list
      insertIntoLinkedList(key, value, table[index]);
    }

    // when load factor is too big, resize and rehash is needed.
    if (getLoadFactor() >= getLoadFactorThreshold()) {
      reSize();
    }
  }

  /**
   * insert helper method for connecting linked node
   *
   * @param key   key to an associated value
   * @param value value associated with the given key
   * @param linkedNode a new linked node will created by key and value
   *                   and connected to this linked node
   */
  private void insertIntoLinkedList(K key, V value, LinkedNode<K, V> linkedNode) {
    // the same key, value is replaced instead of connecting as new node
    if (linkedNode.key.equals(key)) {
      linkedNode.value = value;
      return;
    }

    // traversal to the last of the linked list and add new node at the end
    if (linkedNode.next == null) {
      linkedNode.next = new LinkedNode<>(key, value);
      linkedNode.next.last = linkedNode;
      numKeys++;
      return;
    }
    insertIntoLinkedList(key, value, linkedNode.next);
  }


  /**
   * Remove a key with a value in the hashtable. If the key is not found, return false.
   * Return true otherwise. The key cannot be null
   *
   * @param key   key to an associated value
   * @return true if remove successfully, false otherwise
   * @throws IllegalNullKeyException when key is null
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key should not be null when throw()");
    }

    int index = Math.abs(key.hashCode() % capacity); // index must not be negative

    try {
      LinkedNode<K, V> tmp = findNodeInLinkedList(table[index], key);
      if (tmp.last == null) { // remove the first node
        table[index] = tmp.next;
        if (tmp.next != null) { // if the first node has a next node
          tmp.next.last = null;
        }

      } else { // remove a node which is not the head of the linked list
        tmp.last.next = tmp.next;
        if (tmp.next != null) { // if the node has a next node
          tmp.next.last = tmp.last;
        }
      }
    } catch (KeyNotFoundException e) {
      return false;
    }

    numKeys--;
    return true;
  }

  /**
   * Get value associated with a given key
   *
   * @param key   key to an associated value
   * @return value associated with the founded key
   * @throws IllegalNullKeyException when key is null
   * @throws KeyNotFoundException when key is not found
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException("the key should not be null when get()");
    }

    int index = Math.abs(key.hashCode() % capacity); // index must not be negative

    return findNodeInLinkedList(table[index], key).value;
  }


  /**
   * Helper method to find a node with a specific key in a linked list
   *
   * @param linkedNode head node of a linked list, which is searched for a specific node
   * @param key   key to an associated value
   * @return a linked node with a specific key in a linked list
   * @throws KeyNotFoundException when key is not found
   * @throws IllegalNullKeyException when key is null
   */
  private LinkedNode<K, V> findNodeInLinkedList(LinkedNode<K, V> linkedNode, K key)
      throws KeyNotFoundException, IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("the key should not be null when get()");
    }

    if (linkedNode == null) {
      throw new KeyNotFoundException("key is not found");
    }

    if (linkedNode.key.equals(key)) { // the node with the specific key is found
      return linkedNode;
    } else {
      return findNodeInLinkedList(linkedNode.next, key);
    }
  }

  /**
   * Get element number in the hashtable
   *
   * @return element number in the hashtable
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * Resize and rehash the hashtable
   */
  private void reSize() {
    LinkedNode<K, V>[] oldTable = table;

    // re-initiate capacity and numkeys
    capacity = capacity * 2 + 1;
    numKeys = 0;

    // expand the table with new capacity
    table = new LinkedNode[capacity];

    // traversal the old hashtable and insert all elements into new hashtable
    for (int i = 0; i < oldTable.length; i++) {
      LinkedNode<K, V> pointer = oldTable[i];
      while (pointer != null) {
        try {
          insert(pointer.key, pointer.value);
        } catch (IllegalNullKeyException e) {
          System.out.println("reHash() Unexpected exception");
        }
        pointer = pointer.next;
      }
    }
  }

}
