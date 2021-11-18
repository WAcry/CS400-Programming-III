/**
 * Main.java created by Ziyuan Zhang on Lenovo Yoga 720 in p1 Implement and Test an ADT
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      2/2/2020
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



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * DataStructureADTTest - This class tests if a data structure implementation works wells
 *
 * @param <T> generic type of a data structure implementation
 * @author Ziyuan Zhang (2020)
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T ds; // object to test

  /**
   * Create and return a new T type data structure implementation object
   *
   * @return a new T type data structure implementation object
   */
  protected abstract T createInstance();

  /**
   * Do before all tests
   *
   * @throws Exception // No exceptions
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  /**
   * Do after all tests
   *
   * @throws Exception // No exceptions
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  /**
   * Do before each test
   *
   * @throws Exception // No exceptions
   */
  @BeforeEach
  void setUp() throws Exception {
    ds = createInstance(); // re-initialize tested object
  }

  /**
   * Do after each test
   *
   * @throws Exception // No exceptions
   */
  @AfterEach
  void tearDown() throws Exception {
    ds = null; // clear tested object
  }


  /**
   * Test if the initial size is 0.
   */
  @Test
  void test00_empty_ds_size() {
    if (ds.size() != 0) {
      fail("data structure should be empty, with size=0, but size=" + ds.size());
    }
  }

  /**
   * Insert one key value pair into the data structure and then confirm that size() is 1.
   */
  @Test
  void test01_insert_one() {
    String key = "1";
    String value = "one";

    try {
      ds.insert(key, value);
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
    assert (ds.size() == 1);
  }

  /**
   * Insert one key,value pair and remove it, then confirm size is 0.
   */
  @Test
  void test02_insert_remove_one_size_0() {
    String key = "1";
    String value = "one";

    try {
      ds.insert(key, value);
      assert (ds.remove(key)); // remove the key
      if (ds.size() != 0) {
        fail("data structure should be empty, with size=0, but size=" + ds.size());
      }
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Insert a few key,value pairs such that one of them has the same key as an earlier one.
   * Confirm that a RuntimeException is thrown.
   */
  @Test
  void test03_duplicate_exception_thrown() {
    String key = "1";
    String value = "one";

    try {
      ds.insert("1", "one");
      ds.insert("2", "two");
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }

    try {
      ds.insert(key, value);
      fail("duplicate exception not thrown");
    } catch (RuntimeException re) {
      // excepted exception caught
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
    assert (ds.size() == 2);
  }


  /**
   * Insert some key,value pairs, then try removing a key that was not inserted.  Confirm that the
   * return value is false.
   */
  @Test
  void test04_remove_returns_false_when_key_not_present() {
    String key = "1";
    String value = "one";
    try {
      ds.insert(key, value);
      assert (!ds.remove("2")); // remove non-existent key is false
      assert (ds.remove(key));  // remove existing key is true
      if (ds.get(key) != null) {
        fail("get(" + key + ") returned " + ds.get(key) + " which should have been removed");
      }
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Insert one item and fail if unable to remove it
   */
  @Test
  void test05_insert_remove_one() {
    String key = "1";
    String value = "one";
    try {
      ds.insert(key, value);
      if (!ds.remove(key)) {
        fail("fail to remove an existed data using remove(" + key + ") ");
      }

      assert (ds.get(key) == null); // check if the item indeed be removed
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Insert many items and fail if size is not correct
   */
  @Test
  void test06_insert_many_size() {
    int max = 1000; // number of inserts

    try {
      // insert many items
      for (int i = 0; i < max; i++) {
        ds.insert(String.valueOf(i), "value");
      }
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }

    // check if the size correct after many insertions
    if (!(ds.size() == max)) {
      fail("the size is not correct after 100 elements inserted");
    }
  }

  /**
   * Insert two pairs with different keys, but same values; fail if second doesn't insert
   */
  @Test
  void test07_no_duplicates() {
    String key1 = "1";
    String key2 = "2";
    String sameValue = "value";

    try {
      ds.insert(key1, sameValue);
      ds.insert(key2, sameValue);

      // check both of pairs of elements were inserted successfully
      assert ds.remove(key1);
      assert ds.remove(key2);

    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown when different keys and the same values inserted");
    }

  }

  /**
   * Test if contains could return true when search for existed data and return false when search
   * for un-existed data
   */
  @Test
  void test08_contains_existed_and_un_existed_data() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";
    String value2 = "two";
    String key3 = "3";

    try {
      ds.insert(key1, value1);
      ds.insert(key2, value2);

      if (!ds.contains(key1)) {
        fail("An inserted data is failed to be found by contains(" + key1 + ")");
      }
      if (!ds.contains(key2)) {
        fail("An inserted data is failed to be found by contains(" + key2 + ")");
      }
      if (ds.contains(key3)) {
        fail("An un-existed data is found by contains(" + key3 + ")");
      }
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Check if get could return the corresponding value by given key and return null when key is
   * not existed in the list
   */
  @Test
  void test09_get_existed_and_un_existed_data() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";

    try {
      ds.insert(key1, value1);

      if (!ds.get(key1).equals(value1)) {
        fail("An inserted data's value is failed to be got by contains(" + key1 + ")");
      }

      if (ds.get(key2) != null) {
        fail("get(" + key2 + ") does not return null while key " + key2 + " hasn't inserted");
      }
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Check if false returned when contains(null)
   */
  @Test
  void test10_contains_null_key() {
    assert !ds.contains(null); // check if contains(null) returns false
  }

  /**
   * Check if IllegalArgumentException thrown when remove(null)
   */
  @Test
  void test11_remove_null_key() {
    try {
      ds.remove(null);
      fail("remove(null) doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }
  }

  /**
   * Check if IllegalArgumentException thrown when insert(null)
   */
  @Test
  void test12_insert_null_key() {
    String value1 = "one";

    try {
      ds.insert(null, value1);
      fail("insert(null," + value1 + ") doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

  /**
   * Check if IllegalArgumentException thrown when get(null)
   */
  @Test
  void test13_get_null_key() {
    try {
      ds.get(null);
      fail("get(null) doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }
  }


  /**
   * Insert large number of operations and check if the size correct, then insert and remove for
   * several times, check if all works fine.
   */
  @Test
  void test14_complicated_and_large_data_operations_combined() {
    int max = 10000;
    try {
      for (int i = 0; i < max; i++) {
        ds.insert(String.valueOf(i), String.valueOf(i));
      }
      assert (ds.size() == max);

      ds.remove("0");
      ds.remove("10");
      ds.remove("99");
      assert (ds.size() == max - 3);
      assert (ds.contains("1"));
      assert (!ds.contains("0"));
      assert (ds.get("10") == null);
      assert (ds.get("20").equals("20"));

      ds.insert("0", "0"); // re-insert a key which was removed
      ds.insert("10001", "10001");
      assert (ds.size() == max - 1);
      assert (ds.contains("0"));
      assert (ds.contains("10001"));
      assert (ds.get("10001").equals("10001"));

      ds.remove("10001");
      assert (!ds.contains("10001"));
      assert (ds.size() == max - 2);
      assert (!ds.remove("10001"));
      assert (ds.size() == max - 2);

      ds.insert("10001", "10001");
      ds.insert("10002", "10002");
      ds.insert("10003", "10003");

      assert (ds.size() == max + 1);

      //remove all
      for (int i = 0; i < max + 4; i++) {
        ds.remove(String.valueOf(i));
      }

      assert (ds.size() == 0);
      assert (!ds.contains("0"));
    } catch (Exception e) {
      // should not reached
      fail("Unexpected exception thrown");
    }
  }

}
