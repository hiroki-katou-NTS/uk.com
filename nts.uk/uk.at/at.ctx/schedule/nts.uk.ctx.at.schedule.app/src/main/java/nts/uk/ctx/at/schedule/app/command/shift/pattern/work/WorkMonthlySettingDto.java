/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkMonthlySettingDto.
 */

@Getter
@Setter
public class WorkMonthlySettingDto {

	/** The work monthly setting code. */
	private String workTypeCode;

	/** The working code. */
	private String workingCode;

	/** The work type name. */
	private String workTypeName;

	/** The type color. */
	// ATTENDANCE = 1 , HOLIDAY = 0
	private int typeColor;

	/** The working name. */
	private String workingName;

	/** The date. */
	private String ymdk;

	/** The monthly pattern code. */
	private String monthlyPatternCode;

	public WorkMonthlySettingDto() {
		super();
	}

}
