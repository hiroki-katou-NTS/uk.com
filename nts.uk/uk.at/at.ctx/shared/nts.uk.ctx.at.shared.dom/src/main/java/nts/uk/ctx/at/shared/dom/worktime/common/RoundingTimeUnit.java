/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum RoundingTimeUnit.
 */
//時刻丸め単位
public enum RoundingTimeUnit {
	
	/** The one. */
	ONE(0, "Enum_RoundingTimeUnit_one", "1"),
	
	/** The five. */
	FIVE(1, "Enum_RoundingTimeUnit_five", "5"),
	
	/** The six. */
	SIX(2, "Enum_RoundingTimeUnit_six", "6"),
	
	/** The ten. */
	TEN(3, "Enum_RoundingTimeUnit_ten", "10"),
	
	/** The fifteen. */
	FIFTEEN(4, "Enum_RoundingTimeUnit_fifteen", "15"),
	
	/** The twenty. */
	TWENTY(5, "Enum_RoundingTimeUnit_twenty", "20"),
	
	/** The thirty. */
	THIRTY(6, "Enum_RoundingTimeUnit_thirty", "30"),
	
	/** The sixty. */
	SIXTY(7, "Enum_FontRearSection_sixty", "60");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RoundingTimeUnit[] values = RoundingTimeUnit.values();

	/**
	 * Font rear section.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private RoundingTimeUnit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the font rear section
	 */
	public static RoundingTimeUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingTimeUnit val : RoundingTimeUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
