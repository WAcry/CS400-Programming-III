import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Stores (key,value) pair for each (State,List<DailyStateDataEntry>).
 *
 * This class also provides additional data functionality.
 * @author deppeler ALL RIGHTS RESERVED, FOR STUDENT USE DURING EXAM ONLY.
 */
public class DS {

  /** private fields for your data structure here */

  private TreeMap<String, List<DailyStateDataEntry>> database;
  /** PRO-TIP: use Hashtable or TreeMap  */

  /** PRO-TIP: store as (State,List<DailyStateDataEntry>) pairs. */

  /** initial internal data structure field(s) here */
  public DS() {
    database = new TreeMap<>();
  }

  /** TODO Add a daily entry to the list for a given state */
  public void add(DailyStateDataEntry dataEntry) {
    if (!database.containsKey(dataEntry.getStateName())) {
      database.put(dataEntry.getStateName(), new ArrayList<>());
    }
    database.get(dataEntry.getStateName()).add(dataEntry);
    /**
     * PRO-TIP get state name from record
     * If not found, add to internal ds
     * Then, add this data entry to the correct list for the state.
     */

  }

  /** Return a summary of all the records for specified State */
  public StateSummary getStateSummaryFor(String stateName) {
    /**
     * PRO TIP: use your internal data structure
     * to get list of records for the state and
     * then instantiate and return a StateSummary instance
     */
    return new StateSummary(stateName, database.get(stateName));

  }

  /** Return an array of all state names in sorted order. */
  public String[] getStateNamesInSortedOrder() {
    // complete this method so that it returns an array
    // of all state and territory (no dups) in the data structure

    // There must not be any null elements as the user
    // must be able to use the length to know how many state/territories

    // partial credit if not in sorted order by state name here
    return database.keySet().toArray(new String[0]);
  }

  /**
   * Mini-test code of StateSummary class.
   * STUDENTS MAY EDIT for their own use.
   */
  public static void main(String[] args) {
    DS ds = Main.parseData("test.csv");
    String[] names = ds.getStateNamesInSortedOrder();
    for (String name : names) {
      System.out.print(name + ",");
    }
    System.out.println();
    System.out.println(ds.getStateSummaryFor("Dane"));
    System.out.println(ds.getStateSummaryFor("Green"));
    System.out.println(ds.getStateSummaryFor("Milwaukee"));
  }

}
