/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule;

/**
 * 勤務情報のエラー状態
 * 
 * @author tutk
 *
 */
public enum ErrorStatusWorkInfo {

	/**
	 * 正常
	 */
	NORMAL(1),

	/**
	 * 就業時間帯が必須なのに設定されていない
	 */
	WORKTIME_ARE_REQUIRE_NOT_SET(2),

	/**
	 * 就業時間帯が不要なのに設定されている
	 */
	WORKTIME_ARE_SET_WHEN_UNNECESSARY(3),

	/**
	 * 勤務種類が削除された
	 */
	WORKTYPE_WAS_DELETE(4),

	/**
	 * 就業時間帯が削除された
	 */
	WORKTIME_WAS_DELETE(5);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ErrorStatusWorkInfo[] values = ErrorStatusWorkInfo.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ErrorStatusWorkInfo(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ErrorStatusWorkInfo valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ErrorStatusWorkInfo val : ErrorStatusWorkInfo.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}