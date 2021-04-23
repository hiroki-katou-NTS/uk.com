package nts.uk.file.at.app.export.yearholidaymanagement;

//比較条件
public enum ComparisonConditions {
	// 以下
	UNDER(0, "以下"),
	// 以上
	OVER(1, "以上");
	

	public int value;

	public String name;

	ComparisonConditions(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static ComparisonConditions valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return OVER;
		}
		// Find value.
		for (ComparisonConditions val : ComparisonConditions.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
