/**
 * CompareDS.java created by Ziyuan Zhang on Lenovo Yoga 720 in p2
 * <p>
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu) Date:      2/28/2020
 * <p>
 * Course:    CS400 Semester:  Spring 2020 Lecture:   001
 * <p>
 * IDE:       Intellij IDEA IDE for Java Developers Version:   2019.3.2 Build id:  193.6015.39
 * <p>
 * Device:    Ziyuan-Yoga720 OS:        Windows 10 Home Version:   1903 OS build:  18362.535
 */


/**
 * CompareDS - This class compares the insertion performance of RBT and BST
 *
 * @author Ziyuan Zhang (2020)
 */
public class CompareDS {

  static int n1 = 100;
  static int n2 = 1000;
  static int n3 = 10000;
  static int n4 = 100000;

  /**
   * Main method of the comparision program
   *
   * @param args input arguments
   */
  public static void main(String[] args) {
    System.out.println("Compare rbt and bst performance");
    System.out.println("Test insertion speed:");

    System.out.println("RBT is doing work for " + n1 + " values");
    System.out.println(
        "It took " + test_RBT_insertion(n1) + " ns to process " + n1 + " items");

    System.out.println("BST is doing work for " + n1 + " values");
    System.out.println(
        "It took " + test_BST_insertion(n1) + " ns to process " + n1 + " items");

    System.out.println("RBT is doing work for " + n2 + " values");
    System.out.println(
        "It took " + test_RBT_insertion(n2) + " ns to process " + n2 + " items");

    System.out.println("BST is doing work for " + n2 + " values");
    System.out.println(
        "It took " + test_BST_insertion(n2) + " ns to process " + n2 + " items");

    System.out.println("RBT is doing work for " + n3 + " values");
    System.out.println(
        "It took " + test_RBT_insertion(n3) + " ns to process " + n3 + " items");

    System.out.println("BST is doing work for " + n3 + " values");
    System.out.println(
        "It took " + test_BST_insertion(n3) + " ns to process " + n3 + " items");

    System.out.println("RBT is doing work for " + n4 + " values");
    System.out.println(
        "It took " + test_RBT_insertion(n4) + " ns to process " + n4 + " items");

    System.out.println("BST is doing work for " + n4 + " values");
    System.out.println(
        "It took " + test_BST_insertion(n4) + " ns to process " + n4 + " items");
  }

  /**
   * This method tests RBT's performance for insertions and retrievals
   *
   * @param n amount of the data size
   * @return how long(ns) RBT finish the insert and retrieval operations
   */
  public static long test_RBT_insertion(int n) {
    long startTime = System.nanoTime(); // record start time
    try {
      RBT rbt = new RBT();

      // do n times insertions
      for (int i = 0; i < n; i++) {
        rbt.insert(String.valueOf(i), "value");
      }
    } catch (IllegalNullKeyException e) {
      e.printStackTrace();
      // never reach
    } catch (DuplicateKeyException e) {
      e.printStackTrace();
      // never reach
    } finally {

    }

    long endTime = System.nanoTime(); // record the end time
    return endTime - startTime; // return time-consuming
  }


  /**
   * This method tests DS_Brian's performance for insertions and retrievals
   *
   * @param n amount of the data size
   * @return how long(ns) DS_Brian finish the insert and retrieval operations
   */
  public static long test_BST_insertion(int n) {
    long startTime = System.nanoTime(); // record start time
    try {
      BST bst= new BST();

      // do n times insertions
      for (int i = 0; i < n; i++) {
        bst.insert(String.valueOf(i), "value");
      }
    } catch (IllegalNullKeyException e) {
      e.printStackTrace();
      // never reach
    } catch (DuplicateKeyException e) {
      e.printStackTrace();
      // never reach
    } finally {

    }

    long endTime = System.nanoTime(); // record the end time
    return endTime - startTime; // return time-consuming
  }
}
