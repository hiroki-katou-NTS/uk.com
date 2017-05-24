/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;
/**
 * The Enum VacationType.
 */
public enum AcquisitionType {
	
	/** The Salary. */
	AnnualPaidLeave(1, "年次有給休暇"),
	
	/** The Compensatory day off. */
	CompensatoryDayOff(2, "代休"),
	
	/** The Substitute holiday. */
	SubstituteHoliday(3, "振休"),
	
	/** The Yearly reserved. */
	FundedPaidHoliday(4, "積立年休"),
	
	/** The Over time 60 H. */
	ExsessHoliday(5, "̉60H超休"),
	
	/** The Special holiday. */
	SpecialHoliday(6, "特別休暇");
	
	/** The value. */
	public int value;

	/** The description. */
	public String description;
	
	/** The Constant values. */
	private final static AcquisitionType[] values = AcquisitionType.values();
	
	/**
	 * Instantiates a new vacation type.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private AcquisitionType(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
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
