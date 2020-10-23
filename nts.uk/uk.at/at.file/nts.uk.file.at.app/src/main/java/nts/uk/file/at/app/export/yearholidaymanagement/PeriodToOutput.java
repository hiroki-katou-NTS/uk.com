package nts.uk.file.at.app.export.yearholidaymanagement;

//出力する対象期間
public enum PeriodToOutput {
	// 現在
	CURRENT(0, "現在"),
	// １ 年経過時点
	AFTER_1_YEAR(1, "1年経過時点"),
	// １ 年以上前（過去） - morethan a year ago
	PAST(2, "1年以上前（過去）");
	
	public int value;

	public String name;

	PeriodToOutput(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static PeriodToOutput valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return CURRENT;
		}
		// Find value.
		for (PeriodToOutput val : PeriodToOutput.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
