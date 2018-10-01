/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * 年休端数処理区分
 */
public enum RoundProcessingClassification {
	
	/** Round up to the day. */
	RoundUpToTheDay(0, "Enum_RoundUpToTheDay", "1日に切り上げる"),
	
	/** Truncate on day 0. */
	TruncateOnDay0(1, "Enum_TruncateOnDay0", "0日に切り捨てる"),

	/** Do not perform fractional processing. */
	FractionManagementNo(2, "Enum_FractionManagementNo", "端数処理をしない");

	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static RoundProcessingClassification [] values = RoundProcessingClassification.values();

	/**
	 * Instantiates a new max day reference.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private RoundProcessingClassification(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the max day reference
	 */
	public static RoundProcessingClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundProcessingClassification val : RoundProcessingClassification.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
