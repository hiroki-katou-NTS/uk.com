package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 時刻変更区分
 * @author phongtq
 *
 */
public enum ChangeClockArt {
	
	/** 出勤 */
	GOING_TO_WORK(0),

	/** 退勤 */
	WORKING_OUT(1),

	/** 入門 */
	OVER_TIME(2),

	/** 退門出 */
	BRARK(3),

	/** 応援開始 */
	FIX(4),

	/** 応援終了 */
	END_OF_SUPPORT(5),

	/** 応援出勤 */
	SUPPORT(6),

	/** 外出 */
	GO_OUT(7),

	/** 戻り */
	RETURN(8),

	/** 臨時+応援出勤 */
	TEMPORARY_SUPPORT_WORK(9),

	/** 臨時出勤 */
	TEMPORARY_WORK(10),

	/** 臨時退勤 */
	TEMPORARY_LEAVING(11),

	/** PCログオン */
	PC_LOG_ON(12),

	/** PCログオフ */
	PC_LOG_OFF(13);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ChangeClockArt[] values = ChangeClockArt.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ChangeClockArt(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ChangeClockArt valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ChangeClockArt val : ChangeClockArt.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
