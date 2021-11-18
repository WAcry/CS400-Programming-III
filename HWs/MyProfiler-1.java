/**
 * MyProfiler.java created by Ziyuan Zhang on Lenovo Yoga 720 in p3b HashTable Performance
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      3/25/2020
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


import java.util.TreeMap;


/**
 * This class is used to test our hash table against Tree Map
 *
 * @param <K> A Comparable type to be used as a key to an associated value.
 * @param <V> A value associated with the given key.
 * @author Ziyuan Zhang
 */
public class MyProfiler<K extends Comparable<K>, V> {

  HashTable<K, V> hashtable;
  TreeMap<K, V> treemap;

  /**
   * A default no-arg constructor for MyProfiler
   */
  public MyProfiler() {
    // Instantiate HashTable and Java's TreeMap
    hashtable = new HashTable<>(499979, 1.5); // given a proper table size for this test
    treemap = new TreeMap<>();
  }

  /**
   * Insert the same key with value one time in both treemap and hashtable
   *
   * @param key key to an associated value
   * @param value value associated with the given key
   */
  public void insert(K key, V value){
    try {
      // Insert K, V into both data structures
      hashtable.insert(key, value);
      treemap.put(key, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get value V for key K one time from both treemap and hashtable, for retrieving
   *
   * @param key key to an associated value
   */
  public void retrieve(K key) {

    try {
      // get value V for key K from data structures
      hashtable.get(key);
      treemap.get(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Main method of the comparision program
   *
   * @param args input arguments
   */
  public static void main(String[] args) {
    int numElements = Integer.parseInt(args[0]); // test number from args
    MyProfiler<Integer, String> myProfiler = new MyProfiler<>();

    try {
      // do plenty of times insertions depends on args, testing insert performance
      for (int i = 0; i < numElements; i++) {
        myProfiler.insert(i, "value");
      }

      // do plenty of times get depends on args, testing get performance
      for (int i = 0; i < numElements; i++) {
        myProfiler.retrieve(i);
      }

      String msg = String.format("Inserted and retrieved %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      System.out.println("Usage: java MyProfiler <number_of_elements>");
      System.exit(1);
    }
  }

}
