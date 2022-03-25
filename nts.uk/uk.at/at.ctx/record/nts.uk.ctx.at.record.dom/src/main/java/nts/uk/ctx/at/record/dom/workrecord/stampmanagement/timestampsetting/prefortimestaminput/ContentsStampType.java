package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import nts.uk.shr.com.i18n.TextResource;

/**
 * 打刻種類のDDLの内容
 * @author phongtq
 *
 */
public enum ContentsStampType {
	
	/** 出勤 */
	WORK(1, TextResource.localize("KDP010_250"), "Enum_ContentsStampType_WORK"),

	/** 出勤＋直行 */
	WORK_STRAIGHT(2, TextResource.localize("KDP010_251"), "Enum_ContentsStampType_WORK_STRAIGHT"),

	/** 出勤＋早出 */
	WORK_EARLY(3, TextResource.localize("KDP010_252"), "Enum_ContentsStampType_WORK_EARLY"),
	
	/** 出勤＋休出 */
	WORK_BREAK(4, TextResource.localize("KDP010_253"), "Enum_ContentsStampType_WORK_BREAK"),
	
	/** 退勤 */
	DEPARTURE(5, TextResource.localize("KDP010_254"), "Enum_ContentsStampType_DEPARTURE"),
	
	/** 退勤＋直帰 */
	DEPARTURE_BOUNCE(6, TextResource.localize("KDP010_255"), "Enum_ContentsStampType_DEPARTURE_BOUNCE"),
	
	/** 退勤＋残業 */
	DEPARTURE_OVERTIME(7, TextResource.localize("KDP010_256"), "Enum_ContentsStampType_DEPARTURE_OVERTIME"),
	
	/** 外出 */
	OUT(8, TextResource.localize("KDP010_257"), "Enum_ContentsStampType_OUT"),
	
	/** 戻り */
	RETURN(9, TextResource.localize("KDP010_258"), "Enum_ContentsStampType_RETURN"),
	
	/** 入門 */
	GETTING_STARTED(10, TextResource.localize("KDP010_259"), "Enum_ContentsStampType_GETTING_STARTED"),
	
	/** 退門 */
	DEPAR(11, TextResource.localize("KDP010_260"), "Enum_ContentsStampType_DEPAR"),

	/** 臨時出勤 */
	TEMPORARY_WORK(12, TextResource.localize("KDP010_261"), "Enum_ContentsStampType_TEMPORARY_WORK"),
	
	/** 臨時退勤 */
	TEMPORARY_LEAVING(13, TextResource.localize("KDP010_262"), "Enum_ContentsStampType_TEMPORARY_LEAVING"),
	
	/** 応援開始 */
	START_SUPPORT(14, TextResource.localize("KDP010_263"), "Enum_ContentsStampType_START_SUPPORT"),
	
	/** 応援終了 */
	END_SUPPORT(15, TextResource.localize("KDP010_264"), "Enum_ContentsStampType_END_SUPPORT"),
	
	/** 出勤＋応援 */
	//WORK_SUPPORT(16, TextResource.localize("KDP010_265"), "Enum_ContentsStampType_WORK_SUPPORT"),

	/** 応援開始＋早出 */
	START_SUPPORT_EARLY_APPEARANCE(16, TextResource.localize("KDP010_266"), "Enum_ContentsStampType_START_SUPPORT_EARLY_APPEARANCE"),
	
	/** 応援開始＋休出 */
	START_SUPPORT_BREAK(17, TextResource.localize("KDP010_267"), "Enum_ContentsStampType_START_SUPPORT_BREAK");
	
	/** 予約 */
	//RESERVATION(19, TextResource.localize("KDP010_268"), "Enum_ContentsStampType_RESERVATION"),
	
	/** 予約取消  */
	//CANCEL_RESERVATION(20, TextResource.localize("KDP010_269"), "Enum_ContentsStampType_CANCEL_RESERVATION");
	
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