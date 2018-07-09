package nts.uk.file.at.app.export.attendancerecord;

/**
 * The Enum DayOfWeekJP.
 */
public enum DayOfWeekJP {

	/** The monday. */
	MONDAY("MONDAY", "月"),

	/** The tuesday. */
	TUESDAY("TUESDAY", "火"),

	/** The wednesday. */
	WEDNESDAY("WEDNESDAY", "水"),

	/** The thursday. */
	THURSDAY("THURSDAY", "木"),

	/** The friday. */
	FRIDAY("FRIDAY", "金"),

	/** The saturday. */
	SATURDAY("SATURDAY", "土"),

	/** The sunday. */
	SUNDAY("SUNDAY", "日");

	/** The english. */
	public final String english;

	/** The japanese. */
	public final String japanese;

	/** The Constant values. */
	private final static DayOfWeekJP[] values = DayOfWeekJP.values();

	/**
	 * Instantiates a new day of week JP.
	 *
	 * @param value
	 *            the value
	 * @param display
	 *            the display
	 */
	private DayOfWeekJP(String value, String display) {
		this.english = value;
		this.japanese = display;
	}

	
	/**
	 * Gets the value.
	 *
	 * @param value the value
	 * @return the value
	 */
	public static DayOfWeekJP getValue(String value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DayOfWeekJP val : DayOfWeekJP.values) {
			if (val.english == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
