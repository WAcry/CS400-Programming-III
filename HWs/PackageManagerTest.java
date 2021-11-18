/**
 * Filename:   PackageManagerTest.java
 * Project:    p4
 * Authors:   Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      4/8/2020
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



import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for PackageManager
 */
public class PackageManagerTest {

  PackageManager packageManager;

  /**
   * Run before each test
   */
  @BeforeEach
  void setUp() {
    packageManager = new PackageManager();
  }


  /**
   * test if construct graph function works well
   */
  @Test
  void testPackageManager_001_construct_graph() {
    try {
      packageManager.constructGraph("valid.json");
      ;
      HashSet<String> set = new HashSet<>();
      set.add("A");
      set.add("B");
      set.add("C");
      set.add("D");
      set.add("E");
      set.add("F");
      set.add("G");
      assert set.equals(packageManager.getAllPackages());


    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }


  /**
   * test if cycle could be detected
   */
  @Test
  void testPackageManager_002_cyclic_graph1() {
    try {
      packageManager.constructGraph("cyclic1.json");
      packageManager.getInstallationOrderForAllPackages();
      fail("cyclic exception is not caught");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }

  /**
   * test if cycle could be detected for several cycles
   */
  @Test
  void testPackageManager_003_cyclic_graph2() {
    try {
      packageManager.constructGraph("cyclic2.json");
      packageManager.getInstallationOrderForAllPackages();
      fail("cyclic exception is not caught");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }

  /**
   * check if shared dependencies is not detected wrongly as a cycle
   */
  @Test
  void testPackageManager_004_shared_dependencies() {
    try {
      packageManager.constructGraph("shared_dependencies.json");
      packageManager.getInstallationOrderForAllPackages();
    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }

  /**
   * Test if getInstallationOrder() and toStall method() works as expected
   */
  @Test
  void testPackageManager_005_getInstallationOrder_and_toInstall() {
    try {
      packageManager.constructGraph("valid.json");
      ArrayList<String> list = new ArrayList<>();
      list.add("G");
      list.add("F");
      list.add("D");
      assert packageManager.getInstallationOrder("D").equals(list);

      list.remove("G");
      assert packageManager.toInstall("D", "G").equals(list);
    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }


  /**
   * Test if getPackageWithMaxDependencies() works as expected
   */
  @Test
  void testPackageManager_006_getPackageWithMaxDependencies() {
    try {
      packageManager.constructGraph("valid.json");
      assert packageManager.getPackageWithMaxDependencies().equals("E");
    } catch (Exception e) {
      fail("Unexpected Exception");
    }
  }

}
