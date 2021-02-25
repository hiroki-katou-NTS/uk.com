/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

/**
 * The Enum RemarksContentChoice.
 */
// 備考内容選択肢
public enum RemarksContentChoice {
	
	/** The remarks input. */
	// 備考入力
	REMARKS_INPUT(0, "Enum_RemarksInput", ""),

	/** The master unregistered. */
	// マスタ未登録
	MASTER_UNREGISTERED(1, "Enum_MasterUnregistered", "Enum_MasterUnregistered_Short"),
	
	/** The engraving. */
	// 打刻漏れ
	ENGRAVING(2, "Enum_Engraving", "Enum_Engraving_Short"),
	
	/** The imprinting order not correct. */
	// 打刻順序不正
	IMPRINTING_ORDER_NOT_CORRECT(3, "Enum_ImprintingOrderNotCorrect", "Enum_ImprintingOrderNotCorrect_Short"),
	
	/** The leaving early. */
	// 遅刻早退
	LEAVING_EARLY(4, "Enum_LeavingEarly", "Enum_LeavingEarly_Short"),
	
	/** The holiday stampt. */
	// 休日打刻
	HOLIDAY_STAMP(5, "Enum_HolidayStamp", "Enum_HolidayStamp_Short"),
	
	/** The double engraved. */
	// 二重打刻 
	DOUBLE_ENGRAVED(6, "Enum_DoubleEngraved", "Enum_DoubleEngraved_Short"),
	
	/** The acknowledgment. */
	// 承認反映
	ACKNOWLEDGMENT(7, "Enum_Acknowledgment", "Enum_Acknowledgment_Short"),
	
	/** The manual input. */
	// 手入力
	MANUAL_INPUT(8, "Enum_ManualInput", "Enum_ManualInput_Short"),
	
	/** The not calculated. */
	// 未計算
	NOT_CALCULATED(9, "Enum_NotCalculated", "Enum_NotCalculated_Short"),
	
	/** The exceed by application. */
	// 事前申請超過
	EXCEED_BY_APPLICATION(10, "Enum_ExceedByApplication", "Enum_ExceedByApplication_Short"),

	// 乖離理由
	DIVERGENCE_REASON(11, "Enum_DivergenceReason", "Enum_DivergenceReason_Short"),

	// 乖離エラー
	DEVIATION_ERROR(12, "Enum_DeviationError", "Enum_DeviationError_Short"),

	// 乖離アラーム
	DEVIATION_ALARM(13, "Enum_DeviationAlarm", "Enum_DeviationAlarm_Short"),
	
	/** Added enum by HoangNDH for exporting excel, not exist on design EAP */
	// 遅刻
	LATE_COME(99, "", "Enum_LateCome_Short"),
	
	// Both late come and early leave
	LATE_COME_EARLY_LEAVE(100, "", "Enum_LateCome_EarlyLeave_Short");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** Short name, used in export excel - added by HoangNDH */
	public final String shortName;

	/** The Constant values. */
	private final static RemarksContentChoice[] values = RemarksContentChoice.values();

	
	/**
	 * Instantiates a new remarks content choice.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RemarksContentChoice(int value, String nameId, String shortName) {
		this.value = value;
		this.nameId = nameId;
		this.shortName = shortName;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the remarks content choice
	 */
	public static RemarksContentChoice valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RemarksContentChoice val : RemarksContentChoice.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
