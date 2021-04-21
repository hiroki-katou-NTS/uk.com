package nts.uk.file.at.app.export.yearholidaymanagement;

//参照区分
public enum ReferenceIndicatorType {

	// 実績
	PERFORMANCE(0, "Enum_ReferenceIndicatorType_PERFORMANCE"),
	// 予定・申請含
	SCHEDULE_APLICATION(0, "Enum_ReferenceIndicatorType_SCHEDULE_APLICATION");

	public int value;

	public String name;

	ReferenceIndicatorType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static ReferenceIndicatorType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return PERFORMANCE;
		}
		// Find value.
		for (ReferenceIndicatorType val : ReferenceIndicatorType.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
