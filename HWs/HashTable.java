import java.util.ArrayList;
import javax.xml.validation.SchemaFactoryLoader;

// TODO: comment and complete your HashTableADT implementation
// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//
// TODO: implement all required methods
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object
//       and one of the techniques presented in lecture
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  // TODO: ADD and comment DATA FIELD MEMBERS needed for your implementation
  private int numKeys;
  private int capacity;
  private double loadFactorThreshold;
  private static final int DEFAULT_CAPACITY = 11;
  private static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;
  private static final int COLLISION_RESOLUTION = 8; //TODO: should be 8
  private LinkedNode<K, V>[] table;


  class LinkedNode<K, V> {

    K key;
    V value;
    LinkedNode<K, V> next;
    LinkedNode<K, V> last;

    private LinkedNode() {
      key = null;
      value = null;
      next = null;
      last = null;
    }

    private LinkedNode(K key, V value) {
      this.key = key;
      this.value = value;
      next = null;
      last = null;
    }
  }

  // TODO: comment and complete a default no-arg constructor
  public HashTable() {
    numKeys = 0;
    this.capacity = DEFAULT_CAPACITY;
    table = new LinkedNode[DEFAULT_CAPACITY];
    this.loadFactorThreshold = loadFactorThreshold;
    this.loadFactorThreshold = DEFAULT_LOAD_FACTOR_THRESHOLD;
  }

  // TODO: comment and complete a constructor that accepts
  // initial capacity and load factor threshold
  // threshold is the load factor that causes a resize and rehash
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    numKeys = 0;
    this.capacity = initialCapacity;
    table = new LinkedNode[initialCapacity];
    this.loadFactorThreshold = loadFactorThreshold;
  }

  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  @Override
  public double getLoadFactor() {
    return numKeys / (double) capacity;
  }

  @Override
  public int getCapacity() {
    return capacity;
  }

  @Override
  public int getCollisionResolution() {
    return COLLISION_RESOLUTION;
  }

  @Override
  public void insert(K key, V value) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key should not be null when insert");
    }

    int index = Math.abs(key.hashCode() % capacity);

    if (table[index] == null) {
      table[index] = new LinkedNode<>(key, value);
      numKeys++;
    } else {
      insertIntoLinkedList(key, value, table[index]);
    }

    if (getLoadFactor() >= getLoadFactorThreshold()) {
      reHash();
    }
  }

  private void insertIntoLinkedList(K key, V value, LinkedNode<K, V> linkedNode) {
    if (linkedNode.key.equals(key)) {
      linkedNode.value = value;
      return;
    }

    if (linkedNode.next == null) {
      linkedNode.next = new LinkedNode<>(key, value);
      linkedNode.next.last = linkedNode;
      numKeys++;
      return;
    }

    insertIntoLinkedList(key, value, linkedNode.next);
  }


  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("key should not be null when throw()");
    }

    int index = Math.abs(key.hashCode() % capacity);

    try {
      LinkedNode<K, V> tmp = findNodeInLinkedList(table[index], key);
      if (tmp.last == null) { // first node
        table[index] = tmp.next;
        if (tmp.next != null) {
          tmp.next.last = null;
        }

      } else {
        tmp.last.next = tmp.next;
      }
    } catch (KeyNotFoundException e) {
      return false;
    }

    numKeys--;
    return true;
  }

  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException("the key should not be null when get()");
    }

    int index = Math.abs(key.hashCode() % capacity);
    return findNodeInLinkedList(table[index], key).value;
  }

  private LinkedNode<K, V> findNodeInLinkedList(LinkedNode<K, V> linkedNode, K key)
      throws KeyNotFoundException {
    if (linkedNode == null) {
      throw new KeyNotFoundException("key is not found");
    }
    if (linkedNode.key == key) {
      return linkedNode;
    } else {
      return findNodeInLinkedList(linkedNode.next, key);
    }
  }

  @Override
  public int numKeys() {
    return numKeys;
  }

  private void reHash() {
    // TODO
    LinkedNode<K, V>[] oldTable = table;
    table = new LinkedNode[capacity * 2 + 1];
    capacity = capacity * 2 + 1;
    numKeys = 0;
    for (int i = 0; i < oldTable.length; i++) {
      LinkedNode<K, V> pointer = oldTable[i];
      while (pointer != null) {
        try {
          insert(pointer.key, pointer.value);
        } catch (IllegalNullKeyException e) {
          // should never reach here
          System.out.println("reHash() Unexpected exception");
        }
        pointer = pointer.next;
      }
    }
  }

}
