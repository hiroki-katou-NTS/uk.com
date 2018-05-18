package nts.uk.ctx.sys.assist.dom.deletedata;

public enum OperatingCondition {
	// 準備中
	INPREPARATION(0),

	// 保存中
	SAVING(1),
	
	// 圧縮中
	COMPRESSING(2),

	// 削除中
	DELETING(3),

	// 完了
	DONE(4),

	// 中断終了
	INTERRUPTION_END(5),

	// 異常終了
	ABNORMAL_TERMINATION(6);
	
	/** The value. */
	public final int value;

	
	/** The Constant values. */
	private final static OperatingCondition[] values = OperatingCondition.values();

	/**
	 * Instantiates a new role type.
	 *
	 * @param value the value
	 */
	private OperatingCondition(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static OperatingCondition valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OperatingCondition val : OperatingCondition.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
