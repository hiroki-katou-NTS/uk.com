/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum OriginAtr.
 */
public enum OriginAtr {
	
	/** The legal internal time. */
	// 法定内の残業時間を法定内時間として扱う
	LEGAL_INTERNAL_TIME(0, "Enum_OriginAtr_allEmployee", "法定内の残業時間を法定内時間として扱う"),

	/** The outside legal time. */
	// 法定内の残業時間を法定外時間として扱う
	OUTSIDE_LEGAL_TIME(1, "Enum_OriginAtr_departmentAndChild", "法定内の残業時間を法定外時間として扱う");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static OriginAtr[] values = OriginAtr.values();

	/**
	 * Instantiates a new origin atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private OriginAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the origin atr
	 */
	public static OriginAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OriginAtr val : OriginAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
