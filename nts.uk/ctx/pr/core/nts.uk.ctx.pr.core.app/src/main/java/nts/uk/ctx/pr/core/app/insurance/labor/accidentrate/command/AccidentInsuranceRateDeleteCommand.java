/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto.AccidentInsuranceRateDeleteDto;

/**
 * The Class AccidentInsuranceRateAddCommand.
 */
@Getter
@Setter
public class AccidentInsuranceRateDeleteCommand {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The accident insurance rate. */
	private AccidentInsuranceRateDeleteDto accidentInsuranceRateDeleteDto;

}
