/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * 休憩打刻の時刻管理設定区分
 * The Enum RestClockManageAtr.
 */
public enum RestClockManageAtr {

	/** The is clock manage. */
	// 時刻管理する
	IS_CLOCK_MANAGE(0, "Enum_RestClockManageAtr_useRestTimeToCalc", "時刻管理する"),

	/** The not clock manage. */
	// 時刻管理しない
	NOT_CLOCK_MANAGE(1, "Enum_RestClockManageAtr_useGoOutTimeToCalc", "時刻管理しない");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RestClockManageAtr[] values = RestClockManageAtr.values();

	/**
	 * Instantiates a new rest clock manage atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private RestClockManageAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the rest clock manage atr
	 */
	public static RestClockManageAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RestClockManageAtr val : RestClockManageAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 時刻管理するか判定する
	 * @return　時刻管理をする
	 */
	public boolean isClockManage() {
		return this.equals(IS_CLOCK_MANAGE);
	}
	
	/**
	 * 時刻管理しないか判定する
	 * @return　時刻管理をしない
	 */
	public boolean isNotClockManage() {
		return this.equals(NOT_CLOCK_MANAGE);
	}

}
