package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

/**
 * 申告枠設定
 * @author shuichi_ishida
 */
public enum DeclareFrameSet {

	/** The worktime frame set. */
	// 就業時間帯の枠設定に従う
	WORKTIME_SET(0, "Enum_DeclareFrameSet_WORKTIME_SET", "就業時間帯の枠設定に従う"),

	/** The overtime holidaywork frame set. */
	// 残業休出枠を指定する
	OT_HDWK_SET(1, "Enum_DeclareFrameSet_OT_HDWK_SET", "残業休出枠を指定する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static DeclareFrameSet[] values = DeclareFrameSet.values();

	/**
	 * Instantiates a new declare frame set.
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private DeclareFrameSet(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 * @param value the value
	 * @return the declare frame set
	 */
	public static DeclareFrameSet valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DeclareFrameSet val : DeclareFrameSet.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
