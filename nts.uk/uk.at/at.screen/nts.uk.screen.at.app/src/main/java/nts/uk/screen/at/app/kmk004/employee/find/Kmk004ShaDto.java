/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employee.find;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee.ShaMonthCalSetDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew.ShainStatWorkTimeSetDto;


/**
 * The Class Kmk004ShaDto.
 */
@Getter
@Setter
public class Kmk004ShaDto {
	
	/** The stat work time set dto. */
	private ShainStatWorkTimeSetDto statWorkTimeSetDto;

	/** The month cal set dto. */
	private ShaMonthCalSetDto monthCalSetDto;
}
