/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.company.ComMonthCalSetDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew.ComStatWorkTimeSetDto;

/**
 * The Class Kmk004ComDto.
 */
@Getter
@Setter
@NoArgsConstructor
public class Kmk004ComDto {

	/** The stat work time set dto. */
	private ComStatWorkTimeSetDto statWorkTimeSetDto;

	/** The month cal set dto. */
	private ComMonthCalSetDto monthCalSetDto;
	
	/** The reference flex pred. */
	private int referenceFlexPred;

}
