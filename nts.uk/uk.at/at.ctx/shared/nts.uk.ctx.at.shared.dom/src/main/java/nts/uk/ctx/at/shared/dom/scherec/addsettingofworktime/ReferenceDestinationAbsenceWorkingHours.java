/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum ReferenceDestinationAbsenceWorkingHours.
 */
// 就業時間帯が存在しない場合の参照先
public enum ReferenceDestinationAbsenceWorkingHours {
	
	/** The add time com hd. */
	// 会社一律の休暇加算時間
	ADD_TIME_COM_HD(0, "Enum_AdditionTimeCompanyHoliday"),
	
	/** The add time emp hd. */
	// 社員情報の休暇加算時間
	ADD_TIME_EMP_HD(1, "Enum_AdditionTimeEmployeeHoliday");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static ReferenceDestinationAbsenceWorkingHours[] values = ReferenceDestinationAbsenceWorkingHours.values();

	
	/**
	 * Instantiates a new reference destination absence working hours.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private ReferenceDestinationAbsenceWorkingHours(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the reference destination absence working hours
	 */
	public static ReferenceDestinationAbsenceWorkingHours valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReferenceDestinationAbsenceWorkingHours val : ReferenceDestinationAbsenceWorkingHours.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

