package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The enum Execution Classification
 * 
 * 実行区分
 * 
 * @author sonnh1
 *
 */
public enum ExecutionAtr {
	// 手動
	MANUAL(0, "Enum_Manual", "手動"),

	// 自動
	AUTOMATIC(1, "Enum_Automatic", "自動");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ExecutionAtr[] values = ExecutionAtr.values();

	/**
	 * 
	 * @param value
	 * @param nameId
	 * @param description
	 */
	private ExecutionAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the executionAtr
	 */
	public static ExecutionAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExecutionAtr val : ExecutionAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
