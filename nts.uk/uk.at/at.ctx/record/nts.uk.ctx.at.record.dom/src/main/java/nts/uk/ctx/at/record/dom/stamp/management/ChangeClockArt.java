package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 時刻変更区分
 * @author phongtq
 *
 */
public enum ChangeClockArt {
	
	/** 出勤 */
	GOING_TO_WORK(0,"出勤"),

	/** 退勤 */
	WORKING_OUT(1,"退勤"),

	/** 入門 */
	OVER_TIME(2,"入門"),

	/** 退門出 */
	BRARK(3,"退門出"),

	/** 応援開始 */
	FIX(4,"応援開始 "),

	/** 応援終了 */
	END_OF_SUPPORT(5,"応援終了"),

	/** 応援出勤 */
	SUPPORT(6,"応援出勤"),

	/** 外出 */
	GO_OUT(7,"外出"),

	/** 戻り */
	RETURN(8,"戻り"),

	/** 臨時+応援出勤 */
	TEMPORARY_SUPPORT_WORK(9,"臨時+応援出勤"),

	/** 臨時出勤 */
	TEMPORARY_WORK(10,"臨時出勤"),

	/** 臨時退勤 */
	TEMPORARY_LEAVING(11,"臨時退勤"),

	/** PCログオン */
	PC_LOG_ON(12,"PCログオン"),

	/** PCログオフ */
	PC_LOG_OFF(13,"PCログオフ");

	/** The value. */
	public int value;
	
	/** The value. */
	public String nameId;

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
	private ChangeClockArt(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
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
