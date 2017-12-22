package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

/**
 * @author thanhpv
 * 開始日の指定方法
 */
public enum StartDate {

	/**
	 * 実行日からの日数を指定する
	 */
	NUMBEROFDAYSFROMEXECUTIONDATE(0, "EnumEndDate_NumberOfDaysFromExecutionDate", "実行日からの日数を指定する"),
	
	/**
	 * 締め日を指定する
	 */
	CLOSINGDATE(1, "EnumEndDate_ClosingDate", "締め日を指定する");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static StartDate[] values = StartDate.values();
	
	private StartDate(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	public static StartDate valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (StartDate val : StartDate.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
