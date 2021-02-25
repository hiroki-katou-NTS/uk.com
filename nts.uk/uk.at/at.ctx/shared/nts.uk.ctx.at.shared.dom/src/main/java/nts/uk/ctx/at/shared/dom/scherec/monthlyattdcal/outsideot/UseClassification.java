/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

/**
 * The Enum UseClassification.
 */
// 使用区分
public enum UseClassification {
	
	/** The Use class not use. */
	/* 使用しない*/
	UseClass_NotUse(0),
	
	/** The Use class use. */
	/* 使用する*/
	UseClass_Use(1);
	
	/** The value. */
	public int value;

	/** The Constant values. */
	private final static UseClassification[] values = UseClassification.values();

	/**
	 * Instantiates a new use classification.
	 *
	 * @param value the value
	 */
	private UseClassification(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the use classification
	 */
	public static UseClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UseClassification val : UseClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
