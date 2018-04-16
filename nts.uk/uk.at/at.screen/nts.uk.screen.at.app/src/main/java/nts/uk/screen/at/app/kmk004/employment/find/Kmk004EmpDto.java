/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employment.find;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment.EmpMonthCalSetDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew.EmpStatWorkTimeSetDto;

/**
 * The Class Kmk004EmpDto.
 */
@Getter
@Setter
public class Kmk004EmpDto {
	
	/** The stat work time set dto. */
	private EmpStatWorkTimeSetDto statWorkTimeSetDto;

	/** The month cal set dto. */
	private EmpMonthCalSetDto monthCalSetDto;
}
