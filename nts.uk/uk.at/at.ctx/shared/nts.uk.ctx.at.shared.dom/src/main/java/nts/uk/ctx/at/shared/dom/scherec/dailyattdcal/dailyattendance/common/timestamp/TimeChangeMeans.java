package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

/**
 * 時刻変更手段
 * @author tutk
 *
 */
public enum TimeChangeMeans {
	
	// 実打刻
	REAL_STAMP(0, "Enum_TimeChangeMeans_REAL_STAMP", "実打刻"),
	
	// 申請
	APPLICATION(1, "Enum_TimeChangeMeans_APPLICATION", "申請"),

	// 直行直帰
	DIRECT_BOUNCE(2, "Enum_TimeChangeMeans_DIRECT_BOUNCE", "直行直帰"),
	
	//直行直帰申請
	DIRECT_BOUNCE_APPLICATION(3, "Enum_TimeChangeMeans_DIRECT_BOUNCE_APPLICATION", "直行直帰申請"),
	
	//手修正(本人)
	HAND_CORRECTION_PERSON(4, "Enum_TimeChangeMeans_HAND_CORRECTION_PERSON", "手修正(本人)"),
	
	//手修正(他人)
	HAND_CORRECTION_OTHERS(5, "Enum_TimeChangeMeans_HAND_CORRECTION_OTHERS", "手修正(他人)"),
	
	//自動セット
	AUTOMATIC_SET(6, "Enum_TimeChangeMeans_AUTOMATIC_SET", "自動セット"),
	
	//SPR連携打刻
	SPR_COOPERATION(7, "Enum_TimeChangeMeans_SPR_COOPERATION", "SPR連携打刻");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	/** The Constant values. */
	private final static TimeChangeMeans[] values = TimeChangeMeans.values();

	/**
	 * Instantiates a new completion status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private TimeChangeMeans(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the completion status
	 */
	public static TimeChangeMeans valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeChangeMeans val : TimeChangeMeans.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
