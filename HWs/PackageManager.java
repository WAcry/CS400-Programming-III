/**
 * Filename:   PackageManager.java
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
 *
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 *
 * Each package that depends upon other packages has its own
 * entry in the json file.
 *
 * Package dependencies are important when building software,
 * as you must install packages in an order such that each package
 * is installed after all of the packages that it depends on
 * have been installed.
 *
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 *
 * This program will read package information and
 * provide information about the packages that must be
 * installed before any given package can be installed.
 * all of the packages in
 *
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * A manager program for packages
 *
 * @author Ziyuan Zhang
 */
public class PackageManager {

  private Graph graph; // a graph of packages
  private Package[] packageList; // a list of packages


  /**
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    graph = new Graph();
    packageList = null;
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   *
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(jsonFilepath));
    JSONArray packages = (JSONArray) jo.get("packages");
    Package[] tempList = new Package[packages.size()]; // a list store main packages in json
    HashSet<Package> packageSet = new HashSet<>(); // a set store all packages in json

    // read the json file
    for (int i = 0; i < tempList.length; i++) {
      // get each main package
      JSONObject pack = (JSONObject) packages.get(i);
      String name = (String) pack.get("name");

      // get dependencies of each main package
      JSONArray dependencies = (JSONArray) pack.get("dependencies");
      String[] deps = new String[dependencies.size()];
      for (int j = 0; j < dependencies.size(); j++) {
        deps[j] = (String) dependencies.get(j);
      }

      tempList[i] = new Package(name, deps); // store main packages
    }

    // add all main packages as vertex
    for (Package pack : tempList) {
      graph.addVertex(pack.getName());
      packageSet.add(pack);
    }

    // add all main packages' dependencies as vertex and edges
    for (Package pack : tempList) {
      if (pack.getDependencies() != null) {
        for (String tempDependence : pack.getDependencies()) {
          if (!isDuplicated(packageSet, tempDependence)) {
            // add package with no dependence as a new vertex if it is not a main package
            packageSet.add(new Package(tempDependence, null));
            graph.addVertex(tempDependence);
          }
          graph.addEdge(pack.getName(), tempDependence);
        }
      }
    }

    // transfer the set to packageList
    packageList = new Package[packageSet.size()];
    int i = 0;
    for (Package tempPackage : packageSet) {
      if (i == packageSet.size()) {
        break;
      }
      packageList[i] = tempPackage;
      i++;
    }

  }


  /**
   * A private helper method for constructGraph() to check if a package is already in packageSet
   *
   * @param packageSet // a set store all packages in json
   * @param pack // the package need to check if duplicated
   * @return true if duplicated, false otherwise
   */
  private boolean isDuplicated(HashSet<Package> packageSet, String pack) {
    for (Package tempPkg : packageSet) {
      if (tempPkg.getName().equals(pack)) { // found the duplicated package
        return true;
      }
    }
    return false;
  }

  /**
   * Helper method to get all packages in the graph.
   *
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }


  /**
   * Given a package name, returns a list of packages in a
   * valid installation order.
   *
   * Valid installation order means that each package is listed
   * before any packages that depend upon that package.
   *
   * @return List<String>, order in which the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the
   * specified package, should not throw this exception.
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    List<String> visited = new ArrayList<>();
    getInstallationOrderHelper(visited, pkg); // visit node as inverse installed order by DFS
    Collections.reverse(visited); // reverse the order
    removeDuplicate(visited); // remove duplicated package which has installed before
    return visited;
  }

  /**
   * A helper method for getInstallationOrder() to traverse by DFS
   *
   * @param visited visited list
   * @param pkg start package
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the
   * specified package, should not throw this exception.
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  private void getInstallationOrderHelper(List<String> visited, String pkg)
      throws CycleException, PackageNotFoundException {
    if (visited.contains(pkg)) {
      checkCycle(pkg); // check if a package met twice a part of a cycle
    }

    Package packStart = getPackage(pkg);
    visited.add(pkg);
    String[] dependencies = packStart.getDependencies();
    if (dependencies == null) { // DFS reach an end of a package dependencies link
      return;
    }

    for (String tempDependence : dependencies) { //  do next depth traverse
      getInstallationOrderHelper(visited, tempDependence);
    }
  }

  /**
   * Check if the package a part of a cycle, if so, throw CycleException()
   *
   * @param start the package need to check
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the
   * specified package, should not throw this exception.
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  private void checkCycle(String start) throws PackageNotFoundException, CycleException {
    int[] mark = new int[1];
    checkCycleHelper(start, start, mark);
  }

  /**
   * A helper method for checkCycle(), check if the package a part of a cycle
   *
   * @param current current package access
   * @param start the package need to check
   * @param mark record if the start is met again, which means a cycle
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the
   * specified package, should not throw this exception.
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  private void checkCycleHelper(String current, String start, int[] mark)
      throws PackageNotFoundException, CycleException {
    if (current.equals(start)) {
      mark[0] = mark[0] + 1;
      if (mark[0] == 2) {
        throw new CycleException();
      }
    }
    if (getPackage(current).getDependencies() == null) {
      return;
    }
    for (String dependence : getPackage(current).getDependencies()) {
      checkCycleHelper(dependence, start, mark);
    }

  }


  /**
   * A helper method to get a Package object by string label of the package
   * @param pkg string label of the package
   * @return a Package object corresponding to the string label
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  private Package getPackage(String pkg) throws PackageNotFoundException {
    // traverse the package list to find the package
    for (Package tempPack : packageList) {
      if (pkg.equals(tempPack.getName())) {
        return tempPack;
      }
    }

    throw new PackageNotFoundException();
  }


  /**
   * A helper method for getInstallationOrder(), used to remove duplicated packages
   * which have installed before
   * @param list a list need to remove duplicated
   */
  private static void removeDuplicate(List<String> list) {
    // copy the list without duplicated
    ArrayList<String> result = new ArrayList<String>(list.size());
    for (String str : list) {
      if (!result.contains(str)) { // ignore duplicated
        result.add(str);
      }
    }

    // replace the old list with duplicated elements
    list.clear();
    list.addAll(result);
  }


  /**
   * Given two packages - one to be installed and the other installed,
   * return a List of the packages that need to be newly installed.
   *
   * For example, refer to shared_dependencies.json - toInstall("A","B")
   * If package A needs to be installed and packageB is already installed,
   * return the list ["A", "C"] since D will have been installed when
   * B was previously installed.
   *
   * @return List<String>, packages that need to be newly installed.
   *
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the dependencies of the given packages. If there is a cycle in some other
   * part of the graph that doesn't affect the parsing of these dependencies,
   * cycle exception should not be thrown.
   *
   * @throws PackageNotFoundException if any of the packages passed
   * do not exist in the dependency graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // set A(getInstallationOrder(newPkg))- set B(getInstallationOrder(installedPkg))
    List<String> result = getInstallationOrder(newPkg);
    List<String> installed = getInstallationOrder(installedPkg);
    result.removeAll(installed);
    return result;
  }

  /**
   * Return a valid global installation order of all the packages in the
   * dependency graph.
   *
   * assumes: no package has been installed and you are required to install
   * all the packages
   *
   * returns a valid installation order that will not violate any dependencies
   *
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    ArrayList<String> visited = new ArrayList<>();
    // visit the graph topologically
    getInstallationOrderForAllPackagesHelper(visited, findStarts(visited));
    return visited;
  }

  /**
   * A helper method for getInstallationOrderForAllPackages(), visiting the graph topologically
   * @param visited a list of visited packages
   * @param starts a list of next packages could be seem as a start
   * @throws CycleException if you encounter a cycle in the graph
   */
  private void getInstallationOrderForAllPackagesHelper(ArrayList<String> visited,
      ArrayList<String> starts) throws CycleException {
    if (starts != null) {
      visited.addAll(starts); // mark starts as visited
    }
    if (visited.size() == packageList.length) { // all package is visited
      return;
    }
    // visit the graph topologically
    getInstallationOrderForAllPackagesHelper(visited, findStarts(visited));
  }

  /**
   * Helper methods to find next packages could be seem as a start
   * @param visited a list of visited packages
   * @return a list of next packages could be seem as a start
   * @throws CycleException if you encounter a cycle in the graph
   */
  private ArrayList<String> findStarts(ArrayList<String> visited) throws CycleException {
    ArrayList<String> starts = new ArrayList<>();

    for (Package tempPack : packageList) {
      String[] temp = tempPack.getDependencies();
      if (temp == null) { // the stats is find which has no dependencies
        if (visited != null && visited.isEmpty()) {
          starts.add(tempPack.getName());
        }
        continue;
      }

      // remove visited dependencies
      ArrayList<String> tempDependencies = new ArrayList<>(Arrays.asList(temp));
      if (visited != null) {
        tempDependencies.removeAll(visited);
      }

      // the stats is find which has no dependencies which are not visited
      if (tempDependencies.isEmpty() && visited != null &&
          !visited.contains(tempPack.getName())) {
        starts.add(tempPack.getName());
      }
    }

    // a cycle is found since topological search can't end at expected
    if (starts.size() == 0 && visited != null && visited.size() != packageList.length) {
      throw new CycleException();
    }

    return starts;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   *
   * Tip: it's not just the number of dependencies given in the json file.
   * The number of dependencies includes the dependencies of its dependencies.
   * But, if a package is listed in multiple places, it is only counted once.
   *
   * Example: if A depends on B and C, and B depends on C, and C depends on D.
   * Then,  A has 3 dependencies - B,C and D.
   *
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   * @throws PackageNotFoundException if the package passed does not exist in the
   * dependency graph.
   */
  public String getPackageWithMaxDependencies() throws CycleException, PackageNotFoundException {
    // a list of dependencies amounts of all packages
    int[] dependenciesNumber = new int[packageList.length];
    // traverse all packages and store their dependencies amounts
    for (int i = 0; i < packageList.length; i++) {
      dependenciesNumber[i] = getInstallationOrder(packageList[i].getName()).size();
    }

    // find the largest dependencies amounts's index
    int maxIndex = 0;
    int max = 0;
    for (int i = 0; i < dependenciesNumber.length; i++) {

      if (dependenciesNumber[i] > max) {
        max = dependenciesNumber[i];
        maxIndex = i;
      }
    }

    return packageList[maxIndex].getName();
  }

}
