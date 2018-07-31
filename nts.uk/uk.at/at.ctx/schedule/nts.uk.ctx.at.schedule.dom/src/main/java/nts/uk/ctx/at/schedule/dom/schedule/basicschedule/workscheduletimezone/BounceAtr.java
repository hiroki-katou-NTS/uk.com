/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone;

/**
 * The Enum BounceAtr.
 */
public enum BounceAtr {
	
	/** The no direct bounce. */
	// 直行直帰なし
	NO_DIRECT_BOUNCE(0, "Enum_BounceAtr_noDirectBounce", " 直行直帰なし"),

	/** The directly only. */
	// 直行のみ
	DIRECTLY_ONLY(1, "Enum_BounceAtr_directlyOnly", "直行のみ"),

	/** The bounce only. */
	// 直帰のみ
	BOUNCE_ONLY(2, "Enum_BounceAtr_bounceOnly", "直帰のみ"),
	
	/** The direct bounce. */
	// 直行直帰
	DIRECT_BOUNCE(3, "Enum_BounceAtr_directBounce", "直行直帰");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static BounceAtr[] values = BounceAtr.values();

	/**
	 * Instantiates a new bounce atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private BounceAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the bounce atr
	 */
	public static BounceAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (BounceAtr val : BounceAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
