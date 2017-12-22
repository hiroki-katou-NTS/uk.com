package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

/**
 * @author thanhpv
 * 終了日の指定方法
 */
public enum EndDate {

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
	private final static EndDate[] values = EndDate.values();
	
	private EndDate(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	public static EndDate valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EndDate val : EndDate.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
