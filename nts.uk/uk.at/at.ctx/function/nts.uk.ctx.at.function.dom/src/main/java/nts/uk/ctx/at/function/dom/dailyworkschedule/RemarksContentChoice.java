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
	REMARKS_INPUT(0, "Enum_RemarksInput"),

	/** The master unregistered. */
	// マスタ未登録
	MASTER_UNREGISTERED(1, "Enum_MasterUnregistered"),
	
	/** The engraving. */
	// 打刻漏れ
	ENGRAVING(2, "Enum_Engraving"),
	
	/** The imprinting order not correct. */
	// 打刻順序不正
	IMPRINTING_ORDER_NOT_CORRECT(3, "Enum_ImprintingOrderNotCorrect"),
	
	/** The leaving early. */
	// 遅刻早退
	LEAVING_EARLY(4, "Enum_LeavingEarly"),
	
	/** The holiday stampt. */
	// 休日打刻
	HOLIDAY_STAMP(5, "Enum_HolidayStamp"),
	
	/** The double engraved. */
	// 二重打刻 
	DOUBLE_ENGRAVED(6, "Enum_DoubleEngraved"),
	
	/** The acknowledgment. */
	// 承認反映
	ACKNOWLEDGMENT(7, "Enum_Acknowledgment"),
	
	/** The manual input. */
	// 手入力
	MANUAL_INPUT(8, "Enum_ManualInput"),
	
	/** The not calculated. */
	// 未計算
	NOT_CALCULATED(9, "Enum_NotCalculated"),
	
	/** The exceed by application. */
	// 事前申請超過
	EXCEED_BY_APPLICATION(10, "Enum_ExceedByApplication");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static RemarksContentChoice[] values = RemarksContentChoice.values();

	
	/**
	 * Instantiates a new remarks content choice.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RemarksContentChoice(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
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
