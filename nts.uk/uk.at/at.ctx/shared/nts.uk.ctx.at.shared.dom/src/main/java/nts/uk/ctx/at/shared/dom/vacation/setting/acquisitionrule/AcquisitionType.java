/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

/**
 * The Enum VacationType.
 */
// 休暇の種類
public enum AcquisitionType {

	/** The Salary. */
	AnnualPaidLeave(1, "年次有給休暇", "年次有給休暇"),

	/** The Compensatory day off. */
	CompensatoryDayOff(2, "代休", "代休"),

	/** The Substitute holiday. */
	SubstituteHoliday(3, "振休", "振休"),

	/** The Yearly reserved. */
	FundedPaidHoliday(4, "積立年休", "積立年休"),

	/** The Over time 60 H. */
	ExsessHoliday(5, "60H超休", "60H超休"),

	/** The Special holiday. */
	SpecialHoliday(6, "特別休暇", "特別休暇");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AcquisitionType[] values = AcquisitionType.values();

	/**
	 * Instantiates a new vacation type.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private AcquisitionType(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the acquisition type
	 */
	public static AcquisitionType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AcquisitionType val : AcquisitionType.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
