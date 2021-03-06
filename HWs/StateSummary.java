import java.util.ArrayList;
import java.util.List;

/**
 * Stores total cases and deaths for a state or territory.
 *
 * Each instance stores the results of computing 
 * the total number of cases and deaths for the given state or territory.
 *
 * @author deppeler ALL RIGHTS RESERVED, FOR STUDENT USE DURING EXAM ONLY.
 */
public class StateSummary implements Comparable<StateSummary> {

  /** private data fields */
  private String stateName = "";
  private int totalCases = 0;
  private int totalDeaths = 0;


  /** Return the state name for this summary */
  public String getStateName() {
    return stateName;  // not null, but just an empty string
  }

  /** Return the total number of cases for this state summary */
  public int getTotalCases() {
    return totalCases;  // totally wrong
  }

  /** Return the total number of deaths for this state summary */
  public int getTotalDeaths() {
    return totalDeaths;  // totally wrong
  }

  /**
   * Constructs a StateSummary object that stores data for a single state.
   *
   * @param stateName Name of the state for this list of records
   * @param recordsList a list of available records for this state or territory.
   */
  public StateSummary(String stateName, List<DailyStateDataEntry> recordsList) {

    // PRO-TIP Compute sums of cases and deaths and store sums not data list.
    for (DailyStateDataEntry entry : recordsList) {
      if (entry.getStateName().equals(stateName)) {
        this.stateName = stateName;
        this.totalCases += entry.getCases();
        this.totalDeaths += entry.getDeaths();
      }
    }


  }

  /**
   * DO NOT EDIT Returns a comma-separated string representation of this summary.
   * For example:
   *     Wisconsin,123,12
   *     Illinois,2390,431
   */
  public String toString() {
    return getStateName() + "," + getTotalCases() + "," + getTotalDeaths();
  }

  /**
   * DO NOT EDIT Returns a formatted view of StateSummary record.
   *
   * 	name width = 30 ; cases width = 8 ; deaths width = 8
   *
   *                    Alaska     8028      159
   *            Virgin Islands     1548       45
   *  Northern Mariana Islands      321       48
   *
   */
  public String toFormattedString() {
    return String.format("%30s %8d %8d",
        getStateName(), getTotalCases(), getTotalDeaths());
  }

  /**
   * Mini-test code of StateSummary class.
   * STUDENTS MAY EDIT for their own use.
   */
  public static void main(String[] args) {
    DailyStateDataEntry w1 = DailyStateDataEntry.parse("d1001,Wisconsin,123,12");
    DailyStateDataEntry w2 = DailyStateDataEntry.parse("d2245,Wisconsin,239,43");

    List<DailyStateDataEntry> dataList = new ArrayList<DailyStateDataEntry>();
    dataList.add(w1);
    dataList.add(w2);

    StateSummary ss = new StateSummary("Wisconsin", dataList);
    System.out.println(ss);

    System.out.printf("%30s %8s %8s\n", "State", "Cases", "Deaths");
    System.out.println(ss.toFormattedString());
  }


  @Override
  public int compareTo(StateSummary o) {
    return Integer.compare(this.getTotalCases(), o.getTotalCases());
  }
}
