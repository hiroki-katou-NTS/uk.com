/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum LateEarlyAtr.
 */
//遅刻早退区分
public enum LateEarlyAtr {

	/** The late. */
	// 遅刻
	LATE(0, "Enum_LateEarlyAtr_allEmployee", "遅刻"),

	/** The early. */
	// 早退
	EARLY(1, "Enum_LateEarlyAtr_departmentAndChild", "早退");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static LateEarlyAtr[] values = LateEarlyAtr.values();

	/**
	 * Instantiates a new late early atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private LateEarlyAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the late early atr
	 */
	public static LateEarlyAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (LateEarlyAtr val : LateEarlyAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 遅刻か判定する
	 * @return 通常
	 */
	public boolean isLATE() {
		return LATE.equals(this);
	}
	
	/**
	 * 早退か判定する
	 * @return 割増
	 */
	public boolean isEARLY() {
		return EARLY.equals(this);
	}	
	
	
	
}
