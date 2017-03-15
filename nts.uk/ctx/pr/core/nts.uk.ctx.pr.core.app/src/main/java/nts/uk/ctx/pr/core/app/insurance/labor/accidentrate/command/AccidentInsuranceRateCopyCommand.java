/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;

/**
 * The Class AccidentInsuranceRateCopyCommand.
 */
@Getter
@Setter
public class AccidentInsuranceRateCopyCommand {

	/** The history id copy. */
	private String historyIdCopy;

	/** The start month. */
	private Integer startMonth;

	/** The add new. */
	private boolean addNew;
}
