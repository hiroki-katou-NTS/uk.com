package nts.uk.ctx.at.function.dom.attendancerecord.export;

/**
 * The Enum OutputClassification.
 */
// 出力区分
public enum ExportAtr {

	/** The daily. */
	// 日次
	DAILY(1, "日次"),

	/** The monthly. */
	// 月次
	MONTHLY(2, "月次");

	/** The value. */
	public final int value;

	/** The name. */
	public final String name;

	/** The Constant values. */
	private final static ExportAtr[] values = ExportAtr.values();

	/**
	 * Instantiates a new output classification.
	 *
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	private ExportAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the output classification
	 */
	public static ExportAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExportAtr val : ExportAtr.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}

}
