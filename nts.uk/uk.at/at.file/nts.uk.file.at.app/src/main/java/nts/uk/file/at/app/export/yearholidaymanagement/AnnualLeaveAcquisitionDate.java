package nts.uk.file.at.app.export.yearholidaymanagement;

//年休取得日の印字方法
public enum AnnualLeaveAcquisitionDate {
	// 年月日
	YMD(0, "年月日"),
	// 月日
	MD(1,"月日");

	public int value;

	public String name;

	AnnualLeaveAcquisitionDate(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static AnnualLeaveAcquisitionDate valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return MD;
		}
		// Find value.
		for (AnnualLeaveAcquisitionDate val : AnnualLeaveAcquisitionDate.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
