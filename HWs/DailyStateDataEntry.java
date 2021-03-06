
/**
 * Stores data for a single line of the data file.
 * Each line must have this form:
 * "recordID,stateName,numCases,numDeaths" 
 * @author deppeler ALL RIGHTS RESERVED, FOR STUDENT USE DURING EXAM ONLY.
 */
public class DailyStateDataEntry {

	// PRIVATE DATA data fields provided
	private String recordID;
	private String name;
	private int cases;
	private int deaths;


	/**
	 * Parse line of data into a DailyStateDataEntry instance.
	 * @param line a line of data as read from a data file.
	 */
	public static DailyStateDataEntry parse(String line) {
		DailyStateDataEntry entry = new DailyStateDataEntry();
		String[] splits = line.split(",");
		entry.recordID = splits[0];
		entry.name = splits[1];
		entry.cases = Integer.parseInt(splits[2]);
		entry.deaths = Integer.parseInt(splits[3]);

		return entry;
	}

	/** PUBLIC accessor methods provided */
	public String getRecordID() { return recordID;	}
	public String getStateName() { return name; }

	public int getDeaths() { return deaths; }
	public int getCases() { return cases; }

	@Override
	/** CAN BE CHANGEDOVERRIDED BY STUDENT */
	public String toString() {
		return ""+getRecordID()+","+getStateName()+","+getCases()+","+getDeaths();
	}

	/**
	 * Mini-test code of StateSummary class.
	 * STUDENTS MAY EDIT for their own use.
	 */
	public static void main(String[] args) {
		DailyStateDataEntry s = DailyStateDataEntry.parse("1001,Wisconsin,123,12");
		System.out.println(s.getStateName() + "(" + s.getRecordID() + ")");
		System.out.println("Has " + s.getCases() + " cases.");
		System.out.println("And " + s.getDeaths() + " deaths.");

		DailyStateDataEntry t = DailyStateDataEntry.parse("d1005,Illinois,2390,431");
		System.out.println(t.toString());

	}

}
