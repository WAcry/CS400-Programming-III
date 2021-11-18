import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T ds;

  protected abstract T createInstance();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
    ds = createInstance();
  }

  @AfterEach
  void tearDown() throws Exception {
    ds = null;
  }


  @Test
  void test00_empty_ds_size() {
    if (ds.size() != 0) {
      fail("data structure should be empty, with size=0, but size=" + ds.size());
    }
  }

  // TODO: review tests 01 - 04

  @Test
  void test01_insert_one() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (ds.size() == 1);
  }

  @Test
  void test02_insert_remove_one_size_0() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (ds.remove(key)); // remove the key
    if (ds.size() != 0) {
      fail("data structure should be empty, with size=0, but size=" + ds.size());
    }
  }

  @Test
  void test03_duplicate_exception_thrown() {
    String key = "1";
    String value = "one";
    ds.insert("1", "one");
    ds.insert("2", "two");
    try {
      ds.insert(key, value);
      fail("duplicate exception not thrown");
    } catch (RuntimeException re) {
    }
    assert (ds.size() == 2);
  }


  @Test
  void test04_remove_returns_false_when_key_not_present() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    assert (!ds.remove("2")); // remove non-existent key is false
    assert (ds.remove(key));  // remove existing key is true
    if (ds.get(key) != null) {
      fail("get(" + key + ") returned " + ds.get(key) + " which should have been removed");
    }
  }

  // TODO: add tests 05 - 07 as described in assignment

  @Test
  void test05_insert_remove_one() {
    String key = "1";
    String value = "one";
    ds.insert(key, value);
    if (!ds.remove(key)) {
      fail("fail to remove an existed data using remove(" + key + ") ");
    }
  }

  @Test
  void test06_insert_many_size() {
    int max = 100;

    for (int i = 0; i < max; i++) {
      ds.insert(String.valueOf(i), String.valueOf(i));
    }

    if (!(ds.size() == 100)) {
      fail("the size is not correct after 100 elements inserted");
    }
  }

  /**
   *
   */
  @Test
  void test07_no_duplicates() {
    int max = 100;
    String key1 = "101";
    String value1 = "101";
    String key2 = "102";
    String key3 = "103";

    try {
      ds.insert(key1, value1);
      ds.insert(key2, value1);
      for (int i = 0; i < max; i++) {
        ds.insert(String.valueOf(i), String.valueOf(i));
      }
      ds.insert(key3, value1);
      assert ds.remove(key1);
      assert ds.remove(key2);
      assert ds.remove(key3);
      assert ds.size() == 100;

    } catch (Exception e) {
      fail("Throw unexpected exception when different keys and the same values inserted");
    }

  }

  // TODO: add more tests of your own design to ensure that you can detect implementation
  //  that fail
  //  Tip: consider different numbers of inserts and removes and how different combinations of
  //  insert and removes

  @Test
  void test08_contains_existed_data() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";
    String value2 = "two";
    ds.insert(key1, value1);
    ds.insert(key2, value2);
    if (!ds.contains(key1)) {
      fail("An inserted data is failed to be found by contains(" + key1 + ")");
    }
  }

  @Test
  void test09_get() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";
    String value2 = "two";
    ds.insert(key1, value1);
    ds.insert(key2, value2);
    if (!ds.get(key1).equals("one")) {
      fail("An inserted data is failed to be got by contains(" + key1 + ")");
    }
  }

  @Test
  void test10_contains_nonexistent_data() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";
    String value2 = "two";
    ds.insert(key1, value1);
    if (ds.contains(key2)) {
      fail("contains(" + key2 + ") does not return false while key " + key2 + " hasn't inserted");
    }
  }

  @Test
  void test10_get_nonexistent_data() {
    String key1 = "1";
    String value1 = "one";
    String key2 = "2";
    String value2 = "two";
    ds.insert(key1, value1);
    if (ds.get(key2) != null) {
      fail("get(" + key2 + ") does not return null while key " + key2 + " hasn't inserted");
    }
  }

  @Test
  void test11_remove_null_key() {
    String key1 = "1";
    String value1 = "one";
    ds.insert(key1, value1);

    try {
      ds.remove(null);
      fail("remove(null) doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }
  }

  @Test
  void test12_insert_null_key() {
    String key1 = "1";
    String value1 = "one";

    try {
      ds.insert(null, value1);
      fail("insert(null," + key1 + ") doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }
  }


  @Test
  void test13_complicated_operations_combined() {
    int max = 10000;
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

    ds.insert("0", "0");
    ds.insert("10001", "10001");
    assert (ds.size() == max - 1);
    assert (ds.contains("0"));
    assert (ds.contains("10001"));
    assert (ds.get("10001").equals("10001"));

    ds.remove("10001");
    assert (!ds.contains("10001"));
    assert (ds.size() == max - 2);

    assert (!ds.remove("10001"));

    try {
      ds.remove(null);
      fail("remove(null) doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }

    try {
      ds.insert(null, null);
      fail("insert(null,null) doesn't throw a IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // excepted IllegalArgumentException
    }

    assert (ds.size() == max - 2);

    ds.insert("10001", "10001");
    ds.insert("10002", "10002");
    ds.insert("10003", "10003");

    assert (ds.size() == max + 1);

    //remove all
    for (int i = 0; i < max + 3; i++) {
      ds.remove(String.valueOf(i));
    }
  }

}
