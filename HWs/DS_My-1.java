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



/**
 * DS_My - This class represents my implementation of the DataStructureADT
 *
 * @author Ziyuan Zhang (2020)
 */
public class DS_My implements DataStructureADT<String, String> {

  private String[][] list; // a 2-D list storing data as [value][key]
  private int size; // number of the keys with values in the list
  private final int keyIndex = 1; // the index of the row storing keys
  private final int valueIndex = 0; // the index of the row storing values

  /**
   * Constructor of the program, initializing a 2D array with height 2 and length 10
   */
  public DS_My() {
    int initHeight = 2; // height of the list
    int initLength = 10; // initial length of the list
    list = new String[initHeight][initLength];
    size = 0;
  }

  /**
   * Insert a new un-duplicated key with a value in the list
   *
   * @param key   a new un-duplicated key which is not in current list
   * @param value a value corresponding to the key, and value could be duplicated in the list
   * @throws IllegalArgumentException when the arg key is null
   * @throws RuntimeException         when the arg key is already existed in the list
   */
  @Override
  public void insert(String key, String value) {
    if (key == null) { // the key should not be null
      throw new IllegalArgumentException("null key");
    } else if (this.contains(key)) { // the key should not be duplicated
      throw new RuntimeException("duplicate key");
    } else if (size < list[valueIndex].length) { // the list is not full

      // insert the new key and value in the end of the list
      list[valueIndex][size] = value;
      list[keyIndex][size] = key;
      size++;
    } else { // the list is full and needed expansion

      // Create a new list with double size
      String[][] temp = new String[2][list[valueIndex].length * 2];

      // Fill the contents of the old list into the new list
      for (int i = 0; i < list[valueIndex].length; i++) {
        temp[valueIndex][i] = list[valueIndex][i];
        temp[keyIndex][i] = list[keyIndex][i];
      }

      list = temp;
      insert(key, value); // insert the new key and value in the end of the list
    }

  }

  /**
   * Remove a existing key with a value in the list
   *
   * @param key a key which is in current list corresponding to a value
   * @return return true if a key with corresponding value is removed successfully, otherwise
   * return false
   * @throws IllegalArgumentException when the arg key is null
   */
  @Override
  public boolean remove(String key) {
    if (key == null) { // the key should not be null
      throw new IllegalArgumentException("null key");
    } else {
      for (int i = 0; i < size; i++) { // Traverse the list to find the key

        // if the key is found in the list
        if (list[keyIndex][i].equals(key)) {

          // Remove a existing key with a value in the list
          for (int j = i; j < size; j++) {
            list[keyIndex][j] = list[keyIndex][j + 1];
            list[valueIndex][j] = list[valueIndex][j + 1];
          }
          size--;
          return true;
        }
      }
    }

    // if the key is found in the list
    return false;
  }

  /**
   * Get the value of a specific key in the list, return null when the key is not existed
   *
   * @param key a key which is in current list corresponding to a value
   * @return the value of the specific key in the list, null when the key is not existed
   * @throws IllegalArgumentException when the arg key is null
   */
  @Override
  public String get(String key) {
    if (key == null) { // the key should not be null
      throw new IllegalArgumentException("null key");
    } else {
      for (int i = 0; i < size; i++) { // Traverse the list to find the key
        if (list[keyIndex][i].equals(key)) { // if the key is found
          return list[valueIndex][i];
        }
      }
    }

    // if the key is not found
    return null;
  }


  /**
   * Check if the value of a specific key is in the list
   *
   * @param key a key which is in current list corresponding to a value
   * @return return true if the key existed, otherwise return false
   */
  @Override
  public boolean contains(String key) {
    if (key == null) {
      return false; // return false if key is null
    } else {
      for (int i = 0; i < size; i++) { // Traverse the list to find the key
        if (list[keyIndex][i].equals(key)) { // if the key is found
          return true;
        }
      }
    }

    // if the key is not found
    return false;
  }

  /**
   * Getter of number of the keys with values in the list
   *
   * @return number of the keys with values in the list
   */
  @Override
  public int size() {
    return size;
  }

}
    
