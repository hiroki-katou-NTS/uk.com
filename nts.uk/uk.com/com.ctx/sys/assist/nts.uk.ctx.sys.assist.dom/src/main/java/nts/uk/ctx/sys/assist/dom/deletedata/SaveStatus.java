package nts.uk.ctx.sys.assist.dom.deletedata;

public enum SaveStatus {
	// 成功
	SUCCESS(0),
	
	// 中断
	INTERRUPTION(1),

	// 失敗
	FAILURE(2);
	
	/** The value. */
	public final int value;

	
	/** The Constant values. */
	private final static SaveStatus[] values = SaveStatus.values();

	/**
	 * Instantiates a new role type.
	 *
	 * @param value the value
	 */
	private SaveStatus(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static SaveStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SaveStatus val : SaveStatus.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
