/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Enum CalculationClassification.
 */
// 設定方法
public enum MinusSegment {

	// 0として扱わない
	NOT_TREATED_AS_ZERO(0, "Enum_MinusSegment_NOT_TREATED_AS_ZERO", "0として扱わない"),

	// 0として扱う
	TREATED_AS_ZERO(1, "Enum_MinusSegment_TREATED_AS_ZERO", "0として扱う");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static MinusSegment[] values = MinusSegment.values();

	/**
	 * Instantiates a new minus segment.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private MinusSegment(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the emp condition atr
	 */
	public static MinusSegment valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MinusSegment val : MinusSegment.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 0として扱うか判定する
	 * @return 0として扱うならtrue
	 */
	public boolean isTreatedAsZero() {
		return TREATED_AS_ZERO.equals(this);
	}
	
	/**
	 * 0として扱わないか判定する
	 * @return 0として扱わないならtrue
	 */
	public boolean isNotTreatedAsZero() {
		return NOT_TREATED_AS_ZERO.equals(this);
	}	
	
	
}
