package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 打刻種類のDDLの内容
 * @author phongtq
 *
 */
public enum ContentsStampType {
	
	/** 出勤 */
	WORK(1, "出勤", "Enum_ContentsStampType_WORK"),

	/** 出勤＋直行 */
	WORK_STRAIGHT(2, "出勤＋直行", "Enum_ContentsStampType_WORK_STRAIGHT"),

	/** 出勤＋早出 */
	WORK_EARLY(3, "出勤＋早出", "Enum_ContentsStampType_WORK_EARLY"),
	
	/** 出勤＋休出 */
	WORK_BREAK(4, "出勤＋休出", "Enum_ContentsStampType_WORK_BREAK"),
	
	/** 退勤 */
	DEPARTURE(5, "退勤", "Enum_ContentsStampType_DEPARTURE"),
	
	/** 退勤＋直帰 */
	DEPARTURE_BOUNCE(6, "退勤＋直帰", "Enum_ContentsStampType_DEPARTURE_BOUNCE"),
	
	/** 退勤＋残業 */
	DEPARTURE_OVERTIME(7, "退勤＋残業", "Enum_ContentsStampType_DEPARTURE_OVERTIME"),
	
	/** 退勤 */
	OUT(8, "退勤", "Enum_ContentsStampType_OUT"),
	
	/** 戻り */
	RETURN(9, "戻り", "Enum_ContentsStampType_RETURN"),
	
	/** 入門 */
	GETTING_STARTED(10, "入門", "Enum_ContentsStampType_GETTING_STARTED"),
	
	/** 退門 */
	DEPAR(11, "退門", "Enum_ContentsStampType_DEPAR"),

	/** 臨時出勤 */
	TEMPORARY_WORK(12, "臨時出勤", "Enum_ContentsStampType_TEMPORARY_WORK"),
	
	/** 臨時退勤 */
	TEMPORARY_LEAVING(13, "臨時退勤", "Enum_ContentsStampType_TEMPORARY_LEAVING"),
	
	/** 応援開始 */
	START_SUPPORT(14, "応援開始", "Enum_ContentsStampType_START_SUPPORT"),
	
	/** 応援終了 */
	END_SUPPORT(15, "応援終了", "Enum_ContentsStampType_END_SUPPORT"),
	
	/** 出勤＋応援 */
	WORK_SUPPORT(16, "出勤＋応援", "Enum_ContentsStampType_WORK_SUPPORT"),

	/** 応援開始＋早出 */
	START_SUPPORT_EARLY_APPEARANCE(17, "応援開始＋早出", "Enum_ContentsStampType_START_SUPPORT_EARLY_APPEARANCE"),
	
	/** 応援開始＋休出 */
	START_SUPPORT_BREAK(18, "応援開始＋休出", "Enum_ContentsStampType_START_SUPPORT_BREAK"),
	
	/** 予約 */
	RESERVATION(19, "予約", "Enum_ContentsStampType_RESERVATION"),
	
	/** 予約取消  */
	CANCEL_RESERVATION(20, "予約取消 ", "Enum_ContentsStampType_CANCEL_RESERVATION");
	
	/** The value. */
	public int value;
	
	/** The name id. */
	public  String nameId;

	public  String description;


	/** The Constant values. */
	private final static ContentsStampType[] values = ContentsStampType.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ContentsStampType(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ContentsStampType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ContentsStampType val : ContentsStampType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}