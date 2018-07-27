package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * 再作成対象区分
 * 
 * @author sonnh1
 *
 */
public enum RebuildTargetAtr {
	// 全員
	ALL(0, "KSC001_89", "全員"),
	// 対象者のみ
	TARGET_ONLY(1, "KSC001_90", "対象者のみ");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RebuildTargetAtr[] values = RebuildTargetAtr.values();

	private RebuildTargetAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rebuild target atr
	 */
	public static RebuildTargetAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RebuildTargetAtr val : RebuildTargetAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
