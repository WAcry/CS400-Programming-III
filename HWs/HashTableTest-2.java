import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ObjectStreamField;
import java.util.ArrayList;
import javax.swing.Spring;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/** TODO: add class header comments here*/
public class HashTableTest {

  // TODO: add other fields that will be used by multiple tests
  protected HashTable<Integer, String> hashTable;

  // TODO: add code that runs before each test method
  @Before
  public void setUp() throws Exception {
    hashTable = new HashTable();
  }

  // TODO: add code that runs after each test method
  @After
  public void tearDown() throws Exception {
    hashTable = null;
  }

  /**
   * Tests that a HashTable returns an integer code
   * indicating which collision resolution strategy
   * is used.
   * REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9) {
      fail("collision resolution must be indicated with 1-9");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   * Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) { /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  // TODO add your own tests of your implementation


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   * Tests that if new value covers old value when inserting duplicate keys with different values,
   * and test that if the same values with different keys cause collision
   */
  @Test
  public void test002_duplicate_key_and_duplicate_value() {
    try {
      hashTable.insert(1, "a");
      hashTable.insert(2, "a");
      assert hashTable.get(1).equals("a");
      assert hashTable.get(2).equals("a");
      hashTable.insert(1, "b");
      assert hashTable.get(1).equals("b");
      assert hashTable.get(2).equals("a");
    } catch (Exception e) {
      fail("insert duplicate key should not throw exception " + e.getClass().getName());
    }
  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test003_constructor() {
    hashTable = new HashTable(31, 0.7);
    assert hashTable.getCapacity() == 31;
    assert hashTable.getLoadFactorThreshold() == 0.7;
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test004_get_load_factor() {
    hashTable = new HashTable(5, 0.5);
    try {
      assert hashTable.getLoadFactor() == 0;
      hashTable.insert(1, "a");
      assert hashTable.getLoadFactor() == 1 / (double) 5;
      hashTable.insert(2, "b");
      assert hashTable.getLoadFactor() == 2 / (double) 5;
      hashTable.insert(3, "b");
      assert hashTable.getLoadFactor() == 3 / (double) (5 * 2 + 1);

    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when getLoadFactor() or inserts()");
    }

  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test005_num_keys() {
    try {
      assert (hashTable.numKeys() == 0);
      for (int i = 0; i < 100; i++) {
        hashTable.insert(i, "value");
      }
      assert (hashTable.numKeys() == 100);
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts() 100 times");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test006_simple_inserts() {
    try {
      hashTable.insert(1, "value");
      hashTable.insert(2, "value");
      hashTable.insert(3, "3");
      hashTable.get(1);
      hashTable.get(2);
      hashTable.get(3);
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      fail("few inserted keys cannot found by get()");
    }
  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test007_many_different_inserts_with_many_rehash() {
    hashTable = new HashTable<Integer, String>(11, 0.75);
    try {
      for (int i = 1; i <= 100; i++) {
        hashTable.insert(i, "value");
      }

      for (int i = -10; i <= 0; i++) { // negative inserts test
        hashTable.insert(i, "value");
      }

      for (int i = 5; i <= 15; i++) { // duplicate  inserts test
        hashTable.insert(i, "value");
      }

      for (int i = 9999; i >= 9000; i--) {
        hashTable.insert(i, "value");
      }

      if (hashTable.getCapacity() < 200) {
        fail("inserts more than 1000 times, but rehash() less than 5 times");
      }

      for (int i = 1; i <= 100; i++) {
        hashTable.get(i);
      }
      for (int i = -10; i <= 0; i++) {
        hashTable.get(i);
      }

      for (int i = 5; i <= 15; i++) {
        hashTable.get(i);
      }

      for (int i = 9999; i >= 9000; i--) {
        hashTable.get(i);
      }
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      fail("inserted keys many times and then cannot found by get()");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test008_insert_large_number_as_key() {
    try {
      hashTable.insert(99999999, "value");
      hashTable.get(99999999);
      hashTable.insert(Integer.MAX_VALUE, "value");
      hashTable.get(Integer.MAX_VALUE);
      hashTable.insert(Integer.MIN_VALUE, "value");
      hashTable.get(Integer.MIN_VALUE);
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      fail("inserted keys many times and then cannot found by get()");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test009_get_existed_key() {
    try {
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(2, "b");
      hashTable.insert(3, "3");
      assert hashTable.get(1).equals("1");
      assert hashTable.get(2).equals("b");
      assert hashTable.get(3).equals("3");
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      fail("inserted keys cannot found by get()");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test010_get_un_existed_key() {
    try {
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(2, "b");
      hashTable.insert(3, "3");
      assert hashTable.get(4).equals("4");
      fail("get() un-existed key doesn't throw exception, which should");
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      // expected exception thrown
    }
  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test011_insert_null_key() {
    try {
      hashTable.insert(1, null);
    } catch (Exception e) {
      fail("unexpected exception when insert null value");
    }

    try {
      hashTable.insert(null, "2");
    } catch (IllegalNullKeyException e) {
      // expected exception thrown
    } catch (Exception e) {
      fail("unexpected exception rather than IllegalNullKeyException when insert null key");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test012_remove_null_key() {
    try {
      hashTable.insert(1, "1");
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    }

    try {
      hashTable.remove(null);
      fail("IllegalNullKeyException not thrown when remove(null)");
    } catch (IllegalNullKeyException e) {
      // expected exception thrown
    } catch (Exception e) {
      fail("unexpected exception rather than IllegalNullKeyException when insert null key");
    }
  }

  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test013_remove_existed_key() {
    try {
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(3, "3");
      assert hashTable.get(2).equals("2");
    } catch (IllegalNullKeyException e) {
      fail("Unexpected exception thrown when inserts()");
    } catch (KeyNotFoundException e) {
      fail("Unexpected exception thrown when get() existed key");
    }

    try {
      hashTable.remove(2);
      hashTable.get(2);
    } catch (KeyNotFoundException e) {
      // expected exception thrown
    } catch (Exception e) {
      fail("unexpected exception rather than KeyNotFoundException thrown");
    }

  }


  /** IMPLEMENTED AS EXAMPLE FOR YOU
   *
   */
  @Test
  public void test014_different_types_hashtable() {
    HashTable<String, Object> hashTable1 = new HashTable<String, Object>();
    HashTable<Double, String> hashTable2 = new HashTable<Double, String>();
    try {
      hashTable1.insert("1", 1.0);
      hashTable1.insert("5", 5.0);
      hashTable1.insert("4", 4.0);
      hashTable1.insert("2", 2.0);
      hashTable1.insert("3", 3.0);
      hashTable2.insert(1.0, "1");
      hashTable2.insert(5.0, "5");
      hashTable2.insert(4.0, "4");
      hashTable2.insert(2.0, "2");
      hashTable2.insert(3.0, "3");
      assert hashTable1.get("3").equals(3.0);
      assert hashTable1.get("2").equals(2.0);
      assert hashTable2.get(4.0).equals("4");
    } catch (Exception e) {
      fail("unexpected exception thrown for different implemented types of hashtable");
    }
  }
}
