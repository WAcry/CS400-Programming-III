import static org.junit.jupiter.api.Assertions.fail;

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


  @Test
  public void test999_temp_rehash() throws IllegalNullKeyException {
//    try {
    Integer a = -1;
    System.out.println(a.hashCode());
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(3, "3");
      hashTable.insert(4, "4");
      hashTable.insert(5, "5");
      hashTable.insert(5, "5b");
      hashTable.insert(6, "6");
      hashTable.insert(6, "6b");
      hashTable.insert(7, "7");
      hashTable.insert(8, "8");
      hashTable.insert(9, "9");
      hashTable.insert(10, "10");
      hashTable.insert(11, "11");
      hashTable.insert(12, "12");
      hashTable.insert(13, "13");
      hashTable.insert(14, "14");
      hashTable.insert(15, "15");
      hashTable.remove(15);
      hashTable.remove(14);
      hashTable.remove(15);
      hashTable.remove(13);
      hashTable.remove(12);
      hashTable.remove(16);

//    } catch (Exception e) {
//      fail("insert duplicate key should not throw exception " + e.getClass().getName());
//    }
  }
}
